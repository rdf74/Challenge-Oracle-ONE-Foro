package com.alura.domain.topico;

import jakarta.validation.constraints.NotNull;

public record TopicDataCancellation(
		@NotNull
	    Long idTopico,
	    TopicCancellationReason motivo) {

}
