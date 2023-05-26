package ru.tphr.tphr.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tphr.tphr.DTO.PoemDTO;
import ru.tphr.tphr.entities.Comment;
import ru.tphr.tphr.entities.Poem;
import ru.tphr.tphr.repository.security.PoemRepo;

import java.io.IOException;
import java.util.List;

@Service
public class PoemService {

    private PoemRepo poemRepo;

    @Autowired
    public void setPoemRepo(PoemRepo poemRepo) {
        this.poemRepo = poemRepo;
    }

//  сохранение стихотворения в базе данных.
    public Poem savePoemInDB(Poem poem){
        return poemRepo.save(poem);
    }

//  нахождение стиха по идентификатору
    public Poem getPoemById(long id){
        return poemRepo.findById(id).get();
    }

//  получение всех стихотворений по id их автора
    public List<Poem> getAllPoemsByAuthorId(long id){
        return poemRepo.getAllPoemsByAuthorId(id);
    }

//  удаление стихотворения по id. Насчет exception подумать!!!
    public void deletePoem(long id) throws IOException {
        poemRepo.deleteById(id);
    }

//  метод получения PoemDTO
    public PoemDTO getPoemDTO(long id){
        return poemRepo.getPoemDTO(id);
    }
}
