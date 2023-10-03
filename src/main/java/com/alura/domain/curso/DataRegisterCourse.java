package com.alura.domain.curso;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataRegisterCourse(

        @NotBlank
        @Valid
        String name,

        @NotNull
        Category category) {

}
