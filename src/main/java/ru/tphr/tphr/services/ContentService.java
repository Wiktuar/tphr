package ru.tphr.tphr.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tphr.tphr.entities.poem.Content;
import ru.tphr.tphr.repository.ContentRepo;

// в связи с односторонней связью между Content и Poem операции с превью стихотворения
// осуществляются через Content
@Service
public class ContentService {
    private ContentRepo contentRepo;

    @Autowired
    public void setContentRepo(ContentRepo contentRepo) {
        this.contentRepo = contentRepo;
    }

//  сохранеие стихотворения вместе с его превью через его содержимое
    public Content savePoemInDB(Content content){
        return contentRepo.save(content);
    }

//  метод удаления стихотворения через его ID
    public void deletePoemById(long id) {
        contentRepo.deleteById(id);
    }

//  получение содержания стихотворения без дополнительных данных
    public Content findById(long id){
        return contentRepo.findById(id).get();
    }

}
