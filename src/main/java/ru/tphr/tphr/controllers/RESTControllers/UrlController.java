package ru.tphr.tphr.controllers.RESTControllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlController {

    @PostMapping("/targetLogin")
    public ResponseEntity<?> getTargetUrl(){
        System.out.println("working controller...");
        return ResponseEntity.ok().build();
    }
}
