package med.voll.api.domain.consulta;

import med.voll.api.domain.consulta.validaciones.ValidadorDeCancelamiento;
import med.voll.api.domain.consulta.validaciones.ValidadorDeConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultaService {
    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    List<ValidadorDeConsultas> validador;
    @Autowired
    List<ValidadorDeCancelamiento> validadorDeCancelamientos;


    public  DatosDetallesConsulta agendar(DatosAgendarConsulta datosAgendarConsulta){

        if (!pacienteRepository.findById(datosAgendarConsulta.idPaciente()).isPresent()){
            throw new ValidacionDeIntegridad("Este id para el paciente no fue encontrado");
        }

        if(datosAgendarConsulta.idMedico() != null && !medicoRepository.existsById(datosAgendarConsulta.idMedico())){
            throw new ValidacionDeIntegridad("Este id para el medico no fue encontrado");

        }

        validador.forEach(v -> v.validar(datosAgendarConsulta));

        var paciente = pacienteRepository.findById(datosAgendarConsulta.idPaciente()).get();

        var medico = seleccionarMedico(datosAgendarConsulta);

        if (medico == null){
            throw new ValidacionDeIntegridad("No existe medico disponibles para este horario y especialidad");
        }

        var consulta = new Consulta(medico,paciente,datosAgendarConsulta.fecha());
        consultaRepository.save(consulta);

        return new DatosDetallesConsulta(consulta);

    }

    public void cancelar(DatosCancelarConsulta datosCancelarConsulta){
        if(!consultaRepository.existsById(datosCancelarConsulta.idConsulta())){
            throw new ValidacionDeIntegridad("Conusta no se encunetra registrada");
        }

        validadorDeCancelamientos.forEach(v -> v.validar(datosCancelarConsulta));

        var consulta = consultaRepository.getReferenceById(datosCancelarConsulta.idConsulta());

        consulta.cancelar(datosCancelarConsulta.motivoCancelamiento());
    }


    private Medico seleccionarMedico(DatosAgendarConsulta datosAgendarConsulta) {
        if(datosAgendarConsulta.idMedico() != null){
            return  medicoRepository.getReferenceById(datosAgendarConsulta.idMedico());
        }
        if(datosAgendarConsulta.especialidad()== null){
            throw new ValidacionDeIntegridad("debe seleccionar una epecialidad para el medico");
        }

        return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datosAgendarConsulta.especialidad(),datosAgendarConsulta.fecha());
    }

}
