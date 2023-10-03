create table topicos(

    id bigint not null auto_increment,
    title varchar(100) not null,
    message text not null,
    date_of_creation datetime not null,
    topic_status varchar(100) not null,
    author_id bigint not null,
    course_id bigint not null,
    cancellation_reason varchar(100),

    primary key(id),

    constraint fk_topicos_autor_id foreign key(autor_id) references usuarios(id),
    constraint fk_topicos_curso_id foreign key(curso_id) references cursos(id)
);