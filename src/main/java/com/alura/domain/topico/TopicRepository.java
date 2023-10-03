package com.alura.domain.topico;

import com.alura.domain.curso.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TopicRepository extends JpaRepository<Topic,Long> {

    Boolean existsByCursoAndFechaCreacion(Course course, LocalDateTime fechaCreacion);

    Boolean existsByTitulo(String titulo);
}
