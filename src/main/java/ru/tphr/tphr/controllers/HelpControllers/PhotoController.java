package ru.tphr.tphr.controllers.HelpControllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

@RestController
public class PhotoController {

//    @PostMapping("/photo")
//    public HttpStatus getPhoto(@RequestParam("myTextId") String myTextId){
//
//        System.out.println(myTextId);
//        File uploadFolder = new File("C:\\Users\\wiktu\\Desktop\\tempFiles");
//        long preffix = (long)(Math.random() * 1000000);
//        System.out.println(preffix);
//        String resultFileName = preffix+ "_" + file.getOriginalFilename();
//        System.out.println(resultFileName);
//
//            try {
//                file.transferTo(new File(uploadFolder + "\\" + resultFileName));
//                System.out.println(true);
//            } catch (IOException e) {
//                System.out.println("Файл прочитан");
//            }
//
//        return HttpStatus.OK;
//    }

}
