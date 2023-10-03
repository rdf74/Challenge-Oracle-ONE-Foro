package com.alura.domain.topico.validations;

import com.alura.domain.topico.TopicDataRegister;
import com.alura.domain.curso.CourseRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActiveCourse implements TopicValidator {
    @Autowired
    private CourseRepository courseRepository;

    public void validar(TopicDataRegister datos) {
        if(datos.idCurso()==null){
            return;
        }
        var activeCourse = courseRepository.findActivoById(datos.idCurso());
        if(!activeCourse){
            throw new ValidationException("No se puede agregar topicos de cursos inactivos en el sistema");
        }
    }
}