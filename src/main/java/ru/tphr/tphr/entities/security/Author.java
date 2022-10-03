package ru.tphr.tphr.entities.security;

import lombok.Data;
import ru.tphr.tphr.entities.Poem;

import javax.persistence.*;
import java.util.Set;

@Data
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

    @Column(name = "password")
    private String password;

    @Transient
    private String passwordConfirm;

    @Column(name = "activation_code")
    private String activationCode;

    @Column(name = "path_to_Avatar")
    private String pathToAvatar;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private Set<Poem> poems;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "authors_roles",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private Status status;

    //  social nets
    private String vk;
    private String tg;
    private String yt;

    public boolean isActive() {
        return getStatus().equals(Status.ACTIVE);
    }

    //добавление и удаление роли
    public void addRole(Role role){
        this.roles.add(role);
        role.getAuthors().add(this);
    }

    public void removeRole(Role role){
        this.roles.remove(role);
        role.getAuthors().remove(this);
    }

}
