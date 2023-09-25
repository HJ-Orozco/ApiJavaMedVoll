package med.voll.api.domain.paciente;

public record DatosListadoPaciente(Long id,String nombre, String telefono, String documento_identidad, String email) {

    public DatosListadoPaciente(Paciente paciente) {
        this(paciente.getId(),paciente.getNombre(), paciente.getTelefono(), paciente.getDocumento_identidad(), paciente.getEmail());
    }
}
