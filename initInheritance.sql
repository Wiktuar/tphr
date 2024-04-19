use tphr;

# таблица основной сущности, от которой будут наследоваться все остальные (стихи, музыкальные альбомы и т.д.)
create table compositions (
      id              bigint auto_increment primary key,
      header          varchar(40)  not null,
      release_date    varchar(20)  not null,
      file_name       varchar(255) not null,
      author_id       bigint       not null,
#     первое четверостишие или краткая аннотация к видео или прозе
      poem_preview    text         null,
#     ссылка на файл заглавной песни
      song_preview    varchar(255) null,
#     дискриминатор для наследования hibernate
      comp_type       int          not null,
      constraint cmp_auth_id_fk1
          foreign key (author_id) references authors (id)
);

# таблица содержания стихотворения
create table contents (
      content      text    not null,
      author_id    bigint  not null,
      id           bigint  not null,
      primary key (id),
      foreign key (id) references compositions (id),
      foreign key (author_id) references authors (id)
);

# таблица песен
create table songs
(
    id                  bigint auto_increment primary key,
    header              varchar(50)       not null,
    file_url            varchar(255)      not null,
    duration            varchar(15)       not null,
    album_id            bigint            not null,
    constraint album_fk_1
        foreign key (album_id) references compositions (id)
);

# таблица лайков для произведений
create table comps_likes (
     comp_id     bigint not null,
     author_id   bigint not null,
     constraint p_l_fk1
         foreign key (comp_id) references compositions(id),
     constraint p_l_fk2
         foreign key (author_id) references authors(id)
);

# создание таблицы комментариев
create table comments
(
    id            bigint auto_increment primary key,
    text          varchar(702) not null,
    time_stamp    varchar(25)  not null,
    author_id     bigint       null,
    comp_id       bigint       null,
    constraint  author_id_fk3
        foreign key (author_id) references authors (id),
    constraint  poem_id_fk1
        foreign key (comp_id) references compositions(id)
);
