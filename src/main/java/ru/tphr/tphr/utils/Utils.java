package ru.tphr.tphr.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import ru.tphr.tphr.entities.security.Role;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
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

//  метод, который возвращает URL сайта
    public static String getSiteURL(HttpServletRequest request) {
//  в нашем случае siteUrl = http://localhost:8070/
//  в нашем случае request.getServletPath() = "/"; (для общего развития)
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

//  метод, переводящий в строку текущее время. (в базе данных время хранится в виде строки)
    public static String convertTimeToString(){
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
//  k - 24- часовой форват времени, h- 12-ти часовой формат времени
        return new SimpleDateFormat("dd-MM-yyyy kk:mm:ss").format(timestamp);
    }

//  метод, берущий стизотворение и выделяющий из него первые четыре строфы
    public static String getPoemPreview(String[] data){
        return Arrays.stream(data).limit(4).collect(Collectors.joining("<br>"));
    }

//  метод, берущий стихотворение и добавляющий к его строфам тег переноса строки
    public static String editPoem(String[] data){
        return Arrays.stream(data).collect(Collectors.joining("<br>"));
    }

//  метод, берущий стихотворение и удаляющий из строк тег переноса строки
    public static String editPoem(String data){
        String[] arr = data.split("<br>");
        return Arrays.stream(arr).collect(Collectors.joining());
    }
}
