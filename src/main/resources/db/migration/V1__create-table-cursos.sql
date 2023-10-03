CREATE TABLE cursos(

    id bigint not null auto_increment,
    name varchar(100) not null unique,
    category varchar(100) not null,
    active tinyint default 1,

    primary key(id)

);