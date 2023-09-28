package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosCancelarConsulta;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidarHorarioCancelar implements ValidadorDeCancelamiento{
    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DatosCancelarConsulta datosCancelarConsulta) {
        var consulta = consultaRepository.getReferenceById(datosCancelarConsulta.idConsulta());
        var hora = LocalDateTime.now();
        var diferenciaHora = Duration.between(hora,consulta.getData()).toHours();

        if(diferenciaHora < 24){
            throw new ValidationException("Solo se pude cancelar con una antecedencia minima de 24 hr ");
        }
    }
}
