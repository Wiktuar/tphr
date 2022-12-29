package ru.tphr.tphr.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.tphr.tphr.entities.security.Author;
import ru.tphr.tphr.entities.security.PasswordResetToken;
import ru.tphr.tphr.entities.security.Role;
import ru.tphr.tphr.entities.security.Status;
import ru.tphr.tphr.exceptions.TokenExistsException;
import ru.tphr.tphr.repository.security.AuthorRepo;
import ru.tphr.tphr.repository.security.PasswordTokenRepo;
import ru.tphr.tphr.utils.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.UUID;

@Slf4j
@Service
public class AuthorService implements UserDetailsService {

    private AuthorRepo authorRepo;
    private PasswordTokenRepo passwordTokenRepo;
    private PasswordEncoder passwordEncoder;
    private MailSenderService mailSenderService;

    @Autowired
    public void setAuthorRepo(AuthorRepo authorRepo) {
        this.authorRepo = authorRepo;
    }

    @Autowired
    public void setPasswordTokenRepo(PasswordTokenRepo passwordTokenRepo) {
        this.passwordTokenRepo = passwordTokenRepo;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setMailSenderService(MailSenderService mailSenderService) {
        this.mailSenderService = mailSenderService;
    }

    //метод преобразования автора в пользователя Spring Security
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Author author = authorRepo.findByEmail(email);
        if (author == null) {
            throw new UsernameNotFoundException(String.format("User %s not exists", email));
        }

        return new User(
                author.getEmail(), author.getPassword(),
                author.isActive(), author.isActive(),
                author.isActive(), author.isActive(),
                Utils.mapRoleToAuthority(author.getRoles())
        );
    }

    //метод сохранения пользователя после регистрации
    @Transactional
    public boolean saveAuthor(Author author) {
        author.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        author.setPassword(passwordEncoder.encode(author.getPassword()));
        author.setActivationCode(UUID.randomUUID().toString());
        author.setStatus(Status.NO_ACTIVE);
        authorRepo.save(author);

        String message = String.format("Здравствуйте, %s! \n" +
                "для активация Вашего аккаунта на нашем сайте перейдите, пожалуйста, по ссылке \n" +
                "http://localhost:8070/activate/%s", author.getFirstName(), author.getActivationCode());

        mailSenderService.send(author.getEmail(), "Активация Вашего аккаунта на tphr.ru", message);
        return true;
    }

//  метод, обновляющий пароль пользователя по его ID
    @Transactional
    public void updateAuthorById(String password, long id) {
        password = passwordEncoder.encode(password);
        authorRepo.updateAuthor(password, id);
    }

    // активация пользователя по коду активации
    public boolean activateAuthor(String code) {
        Author author = authorRepo.findByActivationCode(code);

        if (author == null)
            return false;

        author.setActivationCode(null);
        author.setStatus(Status.ACTIVE);
        authorRepo.save(author);

        return true;
    }

//    метод получения автора по почте. Необходим для проверки в контроллере
//    на наличие уже зарегистрированных пользователей
//    @Cacheable("author")
    public Author getAuthorByEmail(String email) {
        log.warn("getting user by email: {}", email);
        return authorRepo.findByEmail(email);
    }

//  метод создания токена для сброса пароля
    public void createResetPassToken(Author author, HttpServletRequest request) {
        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, author);

        String message = String.format("<p>Здравсвуйте, %s</p>"
                + "<p>Вы запраивали сброс пароля на нашем сайте.</p>"
                + "<p>Нажмите на ссылке ниже, чтобы сбросить Ваш пароль:</p>"
                + "<p><a href=\"%s\">Change my password</a></p>"
                + "<br>"
                + "<p>Не переходите по ссылке если Вы помните свой прежний пароль "
                + "или не запрашивали опцию сьроса пароля.</p>", author.getFirstName(), Utils.getSiteURL(request) + "/reset/" + token);
        mailSenderService.send(author.getEmail(), "Сброс пароля на tphr.ru", message);
        passwordTokenRepo.save(passwordResetToken);
    }

//  метод, вовзращающий автора по токену для смены пароля
    @Transactional
    public PasswordResetToken getPasswordResetToken(String token){
         PasswordResetToken prt = passwordTokenRepo.findByToken(token);
         if(prt == null) throw new TokenExistsException("Автор не найден. Запросите новую ссылку " +
                 "восстановления пароля или обратитесь в техническую поддержку.");
        return prt;
    }

}

