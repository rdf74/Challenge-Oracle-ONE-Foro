package com.alura.domain.topico;

import java.util.*;


import com.alura.domain.curso.Course;
import com.alura.domain.topico.validations.TopicCancellationValidator;
import com.alura.domain.topico.validations.TopicValidator;
import com.alura.infra.errors.IntegrityValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alura.domain.curso.CourseRepository;
import com.alura.domain.usuario.UserRepository;


@Service
@SuppressWarnings("all")
public class TopicService {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TopicRepository topicRepository;

    private List<TopicValidator> validadores = new ArrayList<>();

    private List<TopicCancellationValidator> validadoresCancelamiento = new ArrayList<>();

    public TopicDataDetail agregar(TopicDataRegister topicDataRegister){

        if(!userRepository.findById(topicDataRegister.idAutor()).isPresent()){
            throw new IntegrityValidation("este id para el usuario no fue encontrado");
        }

        if(topicDataRegister.idCurso()!=null && !courseRepository.existsById(topicDataRegister.idCurso())){
            throw new IntegrityValidation("este id para el curso no fue encontrado");
        }

        validadores.forEach(v-> v.validar(topicDataRegister));

        var autor = userRepository.findById(topicDataRegister.idAutor()).get();

        var curso = courseRepository.findById(topicDataRegister.idCurso()).get();

        if(curso==null){
            throw new IntegrityValidation("no existen topicos disponibles de este curso");
        }

        var topico = new Topic(topicDataRegister.titulo(), topicDataRegister.mensaje(), autor, curso);

        topicRepository.save(topico);

        return new TopicDataDetail(topico);

    }

    public void cancelar(TopicDataCancellation datos) {
        if(!topicRepository.existsById(datos.idTopico())){
            throw new IntegrityValidation("Id del topico introducido no existe");
        }

        validadoresCancelamiento.forEach(v-> v.validar(datos));

        var topico = topicRepository.getReferenceById(datos.idTopico());
        topico.cancel(datos.motivo());
    }

    private Course seleccionarCurso(TopicDataRegister datos) {
        if(datos.idCurso() != null){
            return courseRepository.getReferenceById(datos.idCurso());
        }
        if(datos.category() == null){
            throw new IntegrityValidation("debe seleccionarse una categoria para el curso");
        }
        return courseRepository.seleccionarCursoConCategoria(datos.category());
    }

    public Page<TopicDataDetail> consultar(Pageable paginacion){
        return topicRepository.findAll(paginacion).map(TopicDataDetail::new);
    }
}