package com.alura.domain.curso;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "cursos")
@Entity(name = "Curso")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@Enumerated(EnumType.STRING)
	private Category category;
	private Boolean active = true;

	public Course(DataRegisterCourse dataRegisterCourse) {
		this.name = dataRegisterCourse.name();
		this.category = dataRegisterCourse.category();
		this.active = true;
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
		Course other = (Course) obj;
		if (id == null) {
            return other.id == null;
		} else return id.equals(other.id);
    }

	public void updateData(DataUpdateCourse dataUpdateCourse) {
		if(dataUpdateCourse.name() != null){
			this.name = dataUpdateCourse.name();
		}
	}

	public void desactivateCourse() {
		this.active = false;
	}
}
