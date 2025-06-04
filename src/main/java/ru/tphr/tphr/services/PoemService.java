package ru.tphr.tphr.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tphr.tphr.DTO.CompositionDTO;
import ru.tphr.tphr.DTO.EditPoemDTO;
import ru.tphr.tphr.DTO.LikesPoemDto;
import ru.tphr.tphr.entities.poem.Poem;
import ru.tphr.tphr.repository.PoemRepo;
import ru.tphr.tphr.utils.Utils;

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

//  получение всех стихотворений по email их автора
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

//  метод, позволяющий получить все Poem конкретного пользователя с количеством лайков и комментариев
    @Transactional
    public List<? extends CompositionDTO> getPoemsByUser(String email1, String email2){
        return Utils.sortCompositionList(poemRepo.getPoemsByUser(email1, email2));
    }

//  метод, позволяющий получить все Poem конкретного пользователя с количеством лайков и комментариев
//  по его ID
    @Transactional
    public List<? extends CompositionDTO> getPoemsByAuthorID(String email, long id){
        return Utils.sortCompositionList(poemRepo.getPoemsByAuthorID(email, id));
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

    //  метод проверки существует ли стихотворение с таким названием или нет
    public boolean checkPoemNotExists(String header, String email){
        return poemRepo.getPoemId(header, email) == null;
    }
}
