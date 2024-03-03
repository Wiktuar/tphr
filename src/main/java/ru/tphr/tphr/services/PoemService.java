package ru.tphr.tphr.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tphr.tphr.DTO.EditPoemDTO;
import ru.tphr.tphr.DTO.LikesPoemDto;
import ru.tphr.tphr.entities.poem.Poem;
import ru.tphr.tphr.repository.security.PoemRepo;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class PoemService {

    private PoemRepo poemRepo;

    @Autowired
    public void setPoemRepo(PoemRepo poemRepo) {
        this.poemRepo = poemRepo;
    }

//  метод отдельного сохранения превью стихотворения (необходим для обновления)
    public Poem savePoem(Poem poem){
        return poemRepo.save(poem);
    }

//  получение всех стихотворений по id их автора
    public List<Poem> getAllPoemsByAuthorEmail(String email){
        return poemRepo.getAllPoemsByAuthorEmail(email);
    }

//  метод получения PoemDTO
    public EditPoemDTO getEditPoemDTO(long id){
        return poemRepo.getEditPoemDTO(id);
    }

//  метод, возвращающий стих с лайками и комментариями
    public LikesPoemDto getPoemDtoWithLikesAndComments(String email, long id){
        return poemRepo.getPoemWithLikesAndComments(email, id);
    }

//  метод, возвращающий список пользователей, лайкнувших стихотворение
    public Poem getListOfLikes(long id){
        return poemRepo.getListOfLikes(id);
    }

//  метод, позволяющий получить все Poem с количеством лайков и комментариев
    @Transactional
    public List<LikesPoemDto> getPoemsByUser(String enail1, String email2){
        return poemRepo.getPoemsByUser(enail1, email2);
    }

//  метод, возвращающий список всех стихотворений.
    public List<LikesPoemDto> getAllPoems(String email){
        return poemRepo.getAllPoem(email);
    }

//  метод, возвращающий относительный путь к картинки стихотворения
    public String getPoemFileName(Long id){
        return poemRepo.getPoemFileName(id);
    }

    //  получения списка имен всех файлов картинок перед удалением
    public Set<String> getAllPoemFileNames(){
        return poemRepo.getAllPoemFileNames();
    }
}
