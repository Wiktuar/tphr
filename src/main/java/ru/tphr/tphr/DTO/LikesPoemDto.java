package ru.tphr.tphr.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikesPoemDto {
    private long id;
    private String header;
    private String fileName;
    private String releaseDate;
    private String poemPreview;
    private String content;
    private String email;
    private String firstName;
    private String lastName;
    private String pathToAvatar;
    private int likes;
    private int comments;
    private boolean meLiked;


    public LikesPoemDto(long id, String header, String fileName, String releaseDate, String poemPreview,
                        String email, String firstName, String lastname, String pathToAvatar,
                        int likes, int comments, boolean meLiked) {
        this.id = id;
        this.header = header;
        this.fileName = fileName;
        this.releaseDate = releaseDate;
        this.poemPreview = poemPreview;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastname;
        this.pathToAvatar = pathToAvatar;
        this.likes = likes;
        this.comments = comments;
        this.meLiked = meLiked;
    }

    public LikesPoemDto(long id, String header, String fileName, String releaseDate, int likes, int comments, boolean meLiked) {
        this.id = id;
        this.header = header;
        this.fileName = fileName;
        this.releaseDate = releaseDate;
        this.likes = likes;
        this.comments = comments;
        this.meLiked = meLiked;
    }
}
