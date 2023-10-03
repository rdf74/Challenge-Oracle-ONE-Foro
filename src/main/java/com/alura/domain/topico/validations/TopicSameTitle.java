package com.alura.domain.topico.validations;

import com.alura.domain.topico.TopicDataRegister;
import com.alura.domain.topico.TopicRepository;
import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;

@Component
public class TopicSameTitle implements TopicValidator {

	private TopicRepository repository;
	
	public void validar(TopicDataRegister datos) {
        if(datos.idCurso()==null)
            return;

        var topicoMismoTitulo = repository.existsByTitulo(datos.titulo());
        if(topicoMismoTitulo){
            throw new ValidationException("ya hay un tópico con el mismo título");
        }
    }
}
