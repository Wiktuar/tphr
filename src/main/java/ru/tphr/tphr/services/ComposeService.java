package ru.tphr.tphr.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tphr.tphr.entities.Composition;
import ru.tphr.tphr.entities.poem.Poem;
import ru.tphr.tphr.repository.CompositionRepo;

@Service
public class ComposeService {
    private CompositionRepo compositionRepo;

    @Autowired
    public void setCompositionRepo(CompositionRepo compositionRepo) {
        this.compositionRepo = compositionRepo;
    }

//  метод, возвращающий список авторов, поставивших лайк произведению.  
    public Composition getListOfLikes(long id){
        return compositionRepo.getListOfLikes(id);
    }

//  метод, сохраняющий произведение в базе данных.
    public void save(Composition comp){
        compositionRepo.save(comp);
    }
}
