package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.consulta.DatosCancelarConsulta;

public interface ValidadorDeCancelamiento {
    void validar(DatosCancelarConsulta datosCancelarConsulta);
}
