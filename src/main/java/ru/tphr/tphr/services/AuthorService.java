package ru.tphr.tphr.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.tphr.tphr.entities.security.Author;
import ru.tphr.tphr.entities.security.Role;
import ru.tphr.tphr.entities.security.Status;
import ru.tphr.tphr.repository.security.AuthorRepo;
import ru.tphr.tphr.utils.Utils;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.UUID;

@Service
public class AuthorService implements UserDetailsService {

    private AuthorRepo authorRepo;
    private PasswordEncoder passwordEncoder;
    private MailSenderService mailSenderService;

    @Autowired
    public void setAuthorRepo(AuthorRepo authorRepo) {
        this.authorRepo = authorRepo;
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

        String message = String.format("здравствуйте, %s! \n" +
                                     "для активация Вашего аккаунта на нашем сайте перейдите, пожалуйста, по ссылке \n" +
                "http://localhost:8070/activate/%s", author.getFirstName(), author.getActivationCode());

        mailSenderService.send(author.getEmail(), "Активация Вашего аккаунта на tphr.ru", message);
        return true;
    }

    // активация пользователя по коду активации
    public boolean activateAuthor(String code) {
        Author author = authorRepo.findByActivationCode(code);

        if(author == null) return false;

        author.setActivationCode(null);
        author.setStatus(Status.ACTIVE);
        authorRepo.save(author);

        return true;
    }

//    метод получения автора по почте. Необходим для проверки в контроллере
//    на наличие уже зарегистрированных пользователей
    public Author getAuthorByEmail(String email){
        return authorRepo.findByEmail(email);
    }
}
