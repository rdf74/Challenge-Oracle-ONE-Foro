package com.alura.domain.topico;

import java.time.LocalDateTime;

public record TopicDataDetail(Long id, String title, String message, LocalDateTime dateOfCreation, Status status, Long idAuthor, Long idCourse) {
	public TopicDataDetail(Topic topic) {
        this(topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getDateOfCreation(),
                topic.getStatus(),
                topic.getAuthor().getId(),
                topic.getCourse().getId());
    }
}
