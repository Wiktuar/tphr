package ru.tphr.tphr.entities.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import ru.tphr.tphr.entities.Poem;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //general fields
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Transient
    private String passwordConfirm;

    @Column(name = "activation_code")
    private String activationCode;

    @Column(name = "path_to_Avatar")
    private String pathToAvatar;

    @JsonIgnore
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Poem> poems;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "authors_roles",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @JsonIgnore
    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private Status status;

    //  social nets
    private String vk;
    private String tg;
    private String yt;

    public Author() {

    }

    public Author(String firstName, String lastName, String pathToAvatar) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pathToAvatar = pathToAvatar;
    }

    public boolean isActive() {
        return getStatus().equals(Status.ACTIVE);
    }

//    //добавление и удаление роли
//    public void addRole(Role role){
//        this.roles.add(role);
//        role.getAuthors().add(this);
//    }
//
//    public void removeRole(Role role){
//        this.roles.remove(role);
//        role.getAuthors().remove(this);
//    }

}
