package ru.tphr.tphr.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorCabinetDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String pathToAvatar;
    private String description;
    private String vk;
    private String tg;
    private String yt;
    private String rt;
}
