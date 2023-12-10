package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.DadosCancelamentoConsulta;
import med.voll.api.domain.consulta.validacoes.cancelamento.ValidadorCancelamentoDeConsulta;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.LocalDateTime;

public interface ValidadorAgendamentoDeConsulta {

    void validar(DadosAgendamentoConsulta dados);

    class ValidadorHorarioAntecedencia implements ValidadorCancelamentoDeConsulta {

        @Autowired
        private ConsultaRepository repository;

        @Override
        public void validar(DadosCancelamentoConsulta dados) {

            var consulta = repository.getReferenceById(dados.idConsulta());
            var agora = LocalDateTime.now();
            var diferencaEmHoras = Duration.between(agora, consulta.getData()).toHours();

            if(diferencaEmHoras < 24) {
                throw new ValidacaoException("Consulta deve ser cancelada com antecedência mínima de 24 horas!");
            }
        }
    }
}
