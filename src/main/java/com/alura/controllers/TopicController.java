package com.alura.controllers;

import com.alura.domain.topico.*;
import com.alura.infra.errors.IntegrityValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicController {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private TopicService service;

    @GetMapping
    @Operation(summary = "Obtiene el listado de topicos")
    public ResponseEntity<Page<TopicDataDetail>> listar (@PageableDefault(size = 10) Pageable paginacion) {
        var response = service.consultar(paginacion);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Transactional
    @Operation(
            summary = "registra un topico en la base de datos",
            description = "",
            tags = { "topico", "post" })
    public ResponseEntity agregar(@RequestBody @Valid TopicDataRegister datos)
            throws IntegrityValidation {
        var response = service.agregar(datos);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    @Transactional
    @Operation(summary = "Actualiza los datos de un topico existente")
    public ResponseEntity actualizarTopico(@RequestBody @Valid TopicDataUpdate topicDataUpdate) {
        Topic topic = topicRepository.getReferenceById(topicDataUpdate.id());
        topic.updateData(topicDataUpdate);
        return ResponseEntity.ok(new TopicDataDetail(topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getDateOfCreation(),
                topic.getStatus(), topic.getAuthor().getId(),
                topic.getCourse().getId()));
    }

    @DeleteMapping
    @Transactional
    @Operation(
            summary = "elimina un topico",
            description = "requiere motivo",
            tags = { "consulta", "delete" })
    public ResponseEntity eliminar(@RequestBody @Valid TopicDataCancellation datos)
            throws IntegrityValidation {
        service.cancelar(datos);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene los registros del topico con ID")
    public ResponseEntity<TopicDataDetail> retornaDatosTopico(@PathVariable Long id) {
        Topic topic = topicRepository.getReferenceById(id);
        var datosTopico = new TopicDataDetail(topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getDateOfCreation(),
                topic.getStatus(),
                topic.getAuthor().getId(),
                topic.getCourse().getId());
        return ResponseEntity.ok(datosTopico);
    }

}
