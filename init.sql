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
  author_id     bigint not      null,
  constraint author_id_fk2
       foreign key(author_id) references authors(id)
);


# создание таблицы "Стихи"
create table poems
(
    id           bigint auto_increment
        primary key,
    header       varchar(40)  not null,
    content      text         not null,
    release_date timestamp    null,
    file_name    varchar(255) null,
    author_id    bigint       not null,
    constraint author_id_fk1
        foreign key (author_id) references authors (id)
);

# создание таблицы "Категории"
create table categories
(
    id            bigint auto_increment primary key,
    category_name varchar(30) null
);





