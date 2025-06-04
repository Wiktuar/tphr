package ru.tphr.tphr.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tphr.tphr.DTO.AllComposeDTO;
import ru.tphr.tphr.DTO.CompositionDTO;
import ru.tphr.tphr.entities.AllCompose;
import ru.tphr.tphr.repository.AllComposeRepo;
import ru.tphr.tphr.utils.Utils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
public class AllComposeService {
    private AllComposeRepo allComposeRepo;

    @Autowired
    public void setAllComposeRepo(AllComposeRepo allComposeRepo) {
        this.allComposeRepo = allComposeRepo;
    }

    public List<? extends CompositionDTO> getAllCompose(String email){
        return Utils.sortCompositionList(allComposeRepo.getAllComposeDto(email));
    }

    //  получение всех произведений конкретного пользователя
    public List<? extends CompositionDTO> getAllComposeDtoByAuthorId(String email, long id){
        return Utils.sortCompositionList(allComposeRepo.getAllComposeDtoByAuthorId(email, id));
    }

    public List<AllCompose> getAll(){
        return allComposeRepo.findAll();
    }
}
