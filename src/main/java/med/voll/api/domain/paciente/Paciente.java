package med.voll.api.domain.paciente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.direccion.Direccion;

@Table(name = "pacientes")
@Entity(name = "Paciente")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;
    private String email;
    private String telefono;
    private Boolean activo;
    private String documento_identidad;
    @Embedded
    private Direccion direccion;

    public Paciente(DatosRegistroPaciente datosRegistroPaciente) {
        this.nombre = datosRegistroPaciente.nombre();
        this.email = datosRegistroPaciente.email();
        this.telefono = datosRegistroPaciente.telefono();
        this.activo = true;
        this.documento_identidad = datosRegistroPaciente.documento_identidad();
        this.direccion = new Direccion(datosRegistroPaciente.direccion());
    }

    public void actualizarDatos(DatosAtualizarPaciente datosAtualizarPaciente) {

        if(datosAtualizarPaciente.nombre()!= null){
            this.nombre = datosAtualizarPaciente.nombre();
        }
        if(datosAtualizarPaciente.telefono()!= null) {
            this.telefono = datosAtualizarPaciente.telefono();
        }
        if(datosAtualizarPaciente.email()!= null) {
            this.email = datosAtualizarPaciente.email();
        }
    }

    public void desactivarPaciente() {
        this.activo = false;
    }
}
