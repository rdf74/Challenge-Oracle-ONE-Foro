package com.alura.domain.respuesta;

import java.time.LocalDateTime;

import com.alura.domain.topico.Topic;
import com.alura.domain.usuario.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "respuestas")
@Entity(name = "Respuesta")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Answer {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String message;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
	private Topic topic;
	@Column(name = "date_of_creation")
	private LocalDateTime dateOfCreation = LocalDateTime.now();
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
	private User author;
	private Boolean solution = false;
	
	@Column(name = "cancellation_reason")
    @Enumerated(EnumType.STRING)
	private AnswerCancellationReason reasonForCancellation;

	public Answer(String message, Topic topic, User author) {
		this.message = message;
		this.topic = topic;
		this.dateOfCreation = LocalDateTime.now();
		this.author = author;
		this.solution = false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Answer other = (Answer) obj;
		if (id == null) {
            return other.id == null;
		} else return id.equals(other.id);
    }

	public void solved(Boolean solution) {
		this.solution = true;
	}

	public void updateData(AnswerDataUpdate answerDataUpdate) {
		if (answerDataUpdate.mensaje() != null) {
			this.message = answerDataUpdate.mensaje();
		}
	}
	
	public void delete(AnswerCancellationReason answerCancellationReason) {
		this.reasonForCancellation = answerCancellationReason;
    }
}
