package com.alura.controllers;

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

import com.alura.domain.respuesta.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@ResponseBody
@RequestMapping("/respuestas")
@SecurityRequirement(name = "bearer-key")
public class RespuestaController {

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    private AnswerService answerService;

    @GetMapping
    @Operation(summary = "Obtiene el listado de respuestas")
    public ResponseEntity<Page<AnswerDataDetail>> listar (@PageableDefault(size = 10) Pageable paginacion) {
        var response = answerService.consultar(paginacion);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Transactional
    @Operation(
            summary = "registra una respuesta en la base de datos",
            description = "",
            tags = { "topico", "post" })
    public ResponseEntity agregar(@RequestBody @Valid AnswerDataRegister datos)
            throws IntegrityValidation {
        var response = answerService.agregar(datos);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    @Transactional
    @Operation(summary = "Actualiza el texto de una respuesta existente")
    public ResponseEntity actualizarRespuesta(@RequestBody @Valid AnswerDataUpdate answerDataUpdate) {
        Answer answer = answerRepository.getReferenceById(answerDataUpdate.id());
        answer.updateData(answerDataUpdate);
        return ResponseEntity.ok(new AnswerDataDetail(answer.getId(), answer.getMessage(),
                answer.getTopic().getId(), answer.getDateOfCreation(), answer.getAuthor().getId()));
    }

    @DeleteMapping
    @Transactional
    @Operation(
            summary = "elimina una respuesta",
            description = "requiere motivo",
            tags = { "consulta", "delete" })
    public ResponseEntity eliminar(@RequestBody @Valid AnswerDataCancellation datos)
            throws IntegrityValidation {
        answerService.eliminar(datos);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/topico/{topico_id}")
    @Operation(summary = "Obtiene las respuestas con ID del topico")
    public ResponseEntity<List<AnswerDataDetail>> retornaDatosRespuestaPorTopico(@PathVariable Long topico_id) {
        List<Answer> answers = answerRepository.findAllByTopicoId(topico_id);
        List<AnswerDataDetail> datosRespuestas = new ArrayList<>();

        for (Answer answer : answers) {
            AnswerDataDetail datosRespuesta = new AnswerDataDetail(answer.getId(),
                    answer.getMessage(),
                    answer.getTopic().getId(),
                    answer.getDateOfCreation(),
                    answer.getAuthor().getId());
            datosRespuestas.add(datosRespuesta);
        }
        return ResponseEntity.ok(datosRespuestas);
    }
}
