package com.alura.domain.respuesta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Query("""
            select r from Respuesta r
            where 
            topico.id=:idTopico
            """)
    List<Answer> findAllByTopicoId(Long idTopico);

}