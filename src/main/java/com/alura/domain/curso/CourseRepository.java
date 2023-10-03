package com.alura.domain.curso;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Page<Course> findByActiveTrue(Pageable paginacion);

    @Query("""
			select c from Curso c
			where
			c.category=:category
			order by rand()
			limit 1
			""")
    Course seleccionarCursoConCategoria(Category category);

	@Query("""
            select c.active 
            from Curso c
            where c.id=:idCourse
            """)
	Boolean findActivoById(Long idCourse);
}
