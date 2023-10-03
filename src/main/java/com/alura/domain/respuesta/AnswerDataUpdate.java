package com.alura.domain.respuesta;

import jakarta.validation.constraints.NotNull;

public record AnswerDataUpdate(@NotNull Long id, String mensaje) {
}
