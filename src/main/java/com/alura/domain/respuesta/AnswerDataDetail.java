package com.alura.domain.respuesta;

import java.time.LocalDateTime;

public record AnswerDataDetail(Long id, String message, Long idTopic, LocalDateTime dateOfCreation, Long idAuthor) {
	public AnswerDataDetail(Answer answer) {
        this(answer.getId(),
                answer.getMessage(),
                answer.getTopic().getId(),
                answer.getDateOfCreation(),
                answer.getAuthor().getId());
    }

}
