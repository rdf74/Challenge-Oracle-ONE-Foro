create table respuestas(

    id bigint not null auto_increment,
    message text not null,
    topic_id bigint not null,
    date_of_creation datetime not null,
    author_id bigint not null,
    solution tinyint default 0 not null,
    cancellation_reason varchar(100) ,

    primary key(id),

    constraint fk_respuestas_topico_id foreign key(topico_id) references topicos(id),
    constraint fk_respuestas_autor_id foreign key(autor_id) references usuarios(id)

);