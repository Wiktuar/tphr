package ru.tphr.tphr.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import ru.tphr.tphr.entities.security.Role;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class Utils {

    //метод преобразования ролей в GrantedAuthority для Spring Security
    public static Collection<? extends GrantedAuthority> mapRoleToAuthority(Set<Role> roles) {
        return roles .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    //метод сохранения аватара автора
    public static void saveAuthorsAvatar(String uploadPath, String imagePath){
        String base64Image = imagePath.split(",")[1];
        byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
        try {
            BufferedImage bImg = ImageIO.read(new ByteArrayInputStream(imageBytes));
            File outputFile = new File(uploadPath + "\\avatar.jpg");
            ImageIO.write(bImg, "jpg", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
