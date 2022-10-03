package ru.tphr.tphr.controllers.HelpControllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

@Controller
public class SendAnswerController {
    @GetMapping("/photo")
    public String getPhotoPage(){
        return "photo";
    }

    @PostMapping("/photo")
    public String getPhotoPage(@RequestParam("myTextName") String myTextNam,
                                @RequestParam("myFile") MultipartFile file,
                               @RequestParam("img") String img){
        System.out.println(file.getOriginalFilename());
        System.out.println(myTextNam);
        System.out.println(img);
        String data = img;
        String base64Image = data.split(",")[1];
        byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
        try {
            BufferedImage bImg = ImageIO.read(new ByteArrayInputStream(imageBytes));
            File outputfile = new File("C:\\Users\\wiktu\\Desktop\\tempFiles\\image.jpg");
            ImageIO.write(bImg, "jpg", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "photo";
    }
}
