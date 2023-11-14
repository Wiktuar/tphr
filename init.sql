# создание базы данных
CREATE DATABASE tphr;
USE tphr;

# создание таблицы "Авторы"
create table authors
(
    id              bigint auto_increment
        primary key,
    email           varchar(255) not null,
    first_name      varchar(50)  not null,
    last_name       varchar(100) not null,
    password        varchar(255) not null,
    activation_code varchar(255) null,
    path_to_Avatar  varchar(255) not null,
    status          varchar(40)  not null,
    vk              varchar(255) null,
    tg              varchar(255) null,
    yt              varchar(255) null,
    constraint email
        unique (email)
);

# создание таблицы "Роли"
create table roles
(
    id   bigint auto_increment
        primary key,
    name varchar(40) not null
);

# создание таблицы "Авторы-Роли"
create table authors_roles
(
    author_id bigint not null,
    role_id   bigint not null,
    constraint author_id
        unique (author_id, role_id),
    constraint authors_roles_ibfk_1
        foreign key (author_id) references authors (id),
    constraint authors_roles_ibfk_2
        foreign key (role_id) references roles (id)
);

# создание таблицы "Токены"
use tphr;

create table tokens(
  id            int primary key auto_increment,
  token         varchar(100)    null,
  author_id     bigint          not null,
  constraint author_id_fk2
       foreign key(author_id) references authors(id)
);

create table persistent_logins(
   username      varchar(50) not null,
   series        varchar(64) primary key,
   token varchar(64) not null,
   last_used timestamp not null
);


# создание таблицы "Стихи"
create table poems
(
    id              bigint auto_increment primary key,
    header          varchar(40)  not null,
    poem_preview    text         not null,
    release_date    varchar(20)  not null ,
    file_name       varchar(255) not null,
    author_id       bigint       not null,
    constraint author_id_fk1
        foreign key (author_id) references authors (id)
);

# таблица содержания стихотворения
create table contents (
    content      text    not null,
    id           bigint  not null,
    primary key (id),
    foreign key (id) references poems (id)
);

# создание таблицы "Категории"
create table categories
(
    id            bigint auto_increment primary key,
    category_name varchar(30) null
);

drop table comments;

# создание таблицы комментариев
create table comments
(
    id            bigint auto_increment primary key,
    text          varchar(702) not null,
    time_stamp    varchar(25)  not null,
    author_id     bigint       null,
    poem_id       bigint       null,
    constraint  author_id_fk3
        foreign key (author_id) references authors (id),
    constraint  poem_id_fk1
        foreign key (poem_id) references poems(id)
);

# создание таблицы для лайков
create table poems_likes (
     poem_id     bigint not null,
     author_id   bigint not null,
     unique (poem_id, author_id),
     constraint p_l_fk1
         foreign key (poem_id) references poems(id),
     constraint p_l_fk2
         foreign key (author_id) references authors(id)
);

# скрипт получения из базы данных сущности стихов с лайками и комментариями
SELECT
    p.id, p.header, p.file_name, p.release_date, IFNULL(l_count, 0), IFNULL(c_count, 0), status
FROM
    poems as p
        LEFT JOIN
    (SELECT
         poem_id, author_id, count(poem_id) as l_count,
         sum(IF(author_id = 1, 1, 0)) > 0 as status
     from
         poems_likes
     GROUP BY
         poem_id) as l
    ON
            p.id = l.poem_id

        LEFT JOIN
    (SELECT
         poem_id, count(poem_id) as c_count
     from
         comments
     GROUP BY
         poem_id) as c
    ON
            p.id = c.poem_id;





