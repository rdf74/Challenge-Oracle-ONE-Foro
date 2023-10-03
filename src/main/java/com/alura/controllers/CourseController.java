package com.alura.controllers;

import com.alura.domain.curso.DataRegisterCourse;
import com.alura.domain.curso.DataListCourse;
import com.alura.domain.curso.DataResponseCourse;
import com.alura.domain.curso.DataUpdateCourse;
import com.alura.domain.curso.Course;
import com.alura.domain.curso.CourseRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;


import java.net.URI;

@RestController
@RequestMapping("/cursos")
@SecurityRequirement(name = "bearer-key")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @PostMapping
    @Transactional
    @Operation(summary = "Registra un nuevo curso en la base de datos")
    public ResponseEntity<DataResponseCourse> registerCourse(@RequestBody @Valid DataRegisterCourse dataRegisterCourse,
                                         UriComponentsBuilder uriComponentsBuilder){
        Course course = courseRepository.save(new Course(dataRegisterCourse));
        DataResponseCourse dataResponseCourse = new DataResponseCourse(course.getId(), course.getName(), course.getCategory());
        URI url = uriComponentsBuilder.path("/course/{id}").buildAndExpand(course.getId()).toUri();
        return ResponseEntity.created(url).body(dataResponseCourse);
    }

    @GetMapping
    @Operation(summary = "Obtiene el listado de cursos")
    public ResponseEntity<Page<DataListCourse>> listCourses(@PageableDefault(size = 5) Pageable paginacion){
        return ResponseEntity.ok(courseRepository.findByActiveTrue(paginacion).map(DataListCourse::new));
    }

    @PutMapping
    @Transactional
    @Operation(summary = "Actualiza los datos de un curso existente")
    public ResponseEntity updateCourse(@RequestBody @Valid DataUpdateCourse dataUpdateCourse){
        Course course = courseRepository.getReferenceById(dataUpdateCourse.id());
        course.updateData(dataUpdateCourse);
        return ResponseEntity.ok(new DataResponseCourse(course.getId(), course.getName(), course.getCategory()));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Elimina un curso")
    public ResponseEntity deleteCourse(@PathVariable Long id){
        Course course = courseRepository.getReferenceById(id);
        course.desactivateCourse();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene los registros del curso con ID")
    public ResponseEntity<DataResponseCourse> returnDataCourse(@PathVariable Long id){
        Course course = courseRepository.getReferenceById(id);
        var dataCourse = new DataResponseCourse(course.getId(), course.getName(), course.getCategory());
        return ResponseEntity.ok(dataCourse);
    }

}
