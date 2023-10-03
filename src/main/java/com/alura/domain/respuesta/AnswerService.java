package com.alura.domain.respuesta;

import com.alura.domain.curso.CourseRepository;
import com.alura.domain.respuesta.validations.ValidadorCancelamientoDeRespuesta;
import com.alura.domain.respuesta.validations.ValidadorDeRespuestas;
import com.alura.domain.topico.TopicRepository;
import com.alura.domain.usuario.UserRepository;
import com.alura.infra.errors.IntegrityValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@SuppressWarnings("all")
public class AnswerService {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private AnswerService answerService;

    private List<ValidadorDeRespuestas> validadores = new ArrayList<>();

    private List<ValidadorCancelamientoDeRespuesta> validadoresCancelamiento = new ArrayList<>();
    @Autowired
    private AnswerRepository answerRepository;

    public AnswerDataDetail agregar(AnswerDataRegister answerDataRegister){

        if(!userRepository.findById(answerDataRegister.idAutor()).isPresent()){
            throw new IntegrityValidation("este id para el autor no fue encontrado");
        }
        
        if(answerDataRegister.idTopico()!=null && !topicRepository.existsById(answerDataRegister.idTopico())){
            throw new IntegrityValidation("este id para el topico no fue encontrado");
        }

        validadores.forEach(v-> v.validar(answerDataRegister));

        var topico = topicRepository.findById(answerDataRegister.idTopico()).get();

        var autor = userRepository.findById(answerDataRegister.idAutor()).get();

        if(topico==null){
            throw new IntegrityValidation("no existen respuestas disponibles del tópico");
        }
        var respuesta = new Answer(answerDataRegister.mensaje(),topico,autor);
        answerRepository.save(respuesta);
        return new AnswerDataDetail(respuesta);
    }

	public void eliminar(AnswerDataCancellation datos) {
        if(!answerRepository.existsById(datos.idRespuesta())){
            throw new IntegrityValidation("Id de respuesta introducido no existe");
        }

        validadoresCancelamiento.forEach(v-> v.validar(datos));

        var respuesta = answerRepository.getReferenceById(datos.idRespuesta());
        respuesta.delete(datos.motivo());
    }

    public Page<AnswerDataDetail> consultar(Pageable paginacion){
        return answerRepository.findAll(paginacion).map(AnswerDataDetail::new);
    }
}