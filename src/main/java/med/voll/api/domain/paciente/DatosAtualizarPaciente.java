package med.voll.api.domain.paciente;

import jakarta.validation.constraints.NotNull;

public record DatosAtualizarPaciente(@NotNull Long id, String nombre, String telefono, String email) {
}
