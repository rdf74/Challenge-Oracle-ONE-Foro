package com.alura.domain.curso;

public record DataListCourse(Long id, String name, String category) {

    public DataListCourse(Course course) {

        this(course.getId(),course.getName(),course.getCategory().toString());
    }
}
