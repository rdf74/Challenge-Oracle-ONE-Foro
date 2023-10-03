package com.alura.domain.topico;

import com.alura.domain.curso.Course;
import com.alura.domain.respuesta.Answer;
import com.alura.domain.usuario.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topic {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String message;
	@Column(name = "date_of_creation")
	private LocalDateTime dateOfCreation = LocalDateTime.now();

	@Column(name = "topic_status")
	@Enumerated(EnumType.STRING)
	private Status status = Status.NO_RESPONDIDO;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author_id")
	private User author;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id")
	private Course course;

	@Column(name = "cancellation_reason")
	@Enumerated(EnumType.STRING)
	private TopicCancellationReason topicCancellationReason;

	@OneToMany
	private List<Answer> answers = new ArrayList<>();

	public Topic(String title, String message, User author, Course course) {
		this.title = title;
		this.message = message;
		this.dateOfCreation = LocalDateTime.now();
		this.status = Status.NO_RESPONDIDO;
		this.author = author;
		this.course = course;
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
		Topic other = (Topic) obj;
		if (id == null) {
            return other.id == null;
		} else return id.equals(other.id);
    }

	public void setCourse(Course course) {
		this.course = course;
	}

	public List<Answer> getAnswers() {
		return this.answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public void updateData(TopicDataUpdate topicDataUpdate) {
		if (topicDataUpdate.title() != null) {
			this.title = topicDataUpdate.title();
		}
		if (topicDataUpdate.message() != null) {
			this.message = topicDataUpdate.message();
		}
	}
	public void cancel(TopicCancellationReason topicCancellationReason) {
		this.topicCancellationReason = topicCancellationReason;
	}
	}
