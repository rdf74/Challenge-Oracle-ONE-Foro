package com.alura.domain.respuesta;

import jakarta.validation.constraints.NotNull;

public record AnswerDataCancellation(
		@NotNull
	    Long idRespuesta,
	    AnswerCancellationReason motivo) {

}
