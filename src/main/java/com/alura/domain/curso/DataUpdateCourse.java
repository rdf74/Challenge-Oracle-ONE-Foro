package com.alura.domain.curso;

import jakarta.validation.constraints.NotNull;

public record DataUpdateCourse(@NotNull Long id, String name) {


}
