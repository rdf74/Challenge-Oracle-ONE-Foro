package com.alura.domain.topico;

import com.alura.domain.curso.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TopicDataRegister(
        @NotBlank
        String titulo,
        @NotBlank
        String mensaje,
        @NotNull
        Long idAutor,
        Long idCurso,

        Category category) {

}
