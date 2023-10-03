package com.alura.domain.topico;

import jakarta.validation.constraints.NotNull;

public record TopicDataUpdate(@NotNull Long id, String title, String message) {
}
