package br.com.qa.ai.validator;

import br.com.qa.ai.model.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QAAnalysisValidator {

    private static final Set<String> TIPOS_VALIDOS = Set.of(
            "Positivo",
            "Negativo",
            "Borda"
    );

    private static final Set<String> PRIORIDADES_VALIDAS = Set.of(
            "Alta",
            "Média",
            "Baixa"
    );

    public void validate(
            QAAnalysisResponse response,
            QARequest request) {

        if (response == null) {
            throw new IllegalArgumentException(
                    "A resposta da análise de QA não pode ser nula."
            );
        }

        if (request == null) {
            throw new IllegalArgumentException(
                    "A solicitação de análise de QA não pode ser nula."
            );
        }

        validarResumo(response.resumo());
        validarRiscos(response.riscos());
        validarCenarios(response.cenarios());
        validarCasosDeTeste(response.casosDeTeste());
        validarRastreabilidade(response, request);
    }

    private void validarRastreabilidade(
            QAAnalysisResponse response,
            QARequest request) {

        Set<String> criteriosAceitacao = request.userStory()
                .acceptanceCriteria()
                .stream()
                .map(criterion -> criterion.id())
                .collect(java.util.stream.Collectors.toSet());

        Set<String> regrasNegocio = request.businessRules()
                .stream()
                .map(rule -> rule.id())
                .collect(java.util.stream.Collectors.toSet());

        Set<String> riscos = response.riscos()
                .stream()
                .map(Risk::id)
                .collect(java.util.stream.Collectors.toSet());

        Set<String> cenarios = response.cenarios()
                .stream()
                .map(TestScenario::id)
                .collect(java.util.stream.Collectors.toSet());

        for (TestScenario scenario : response.cenarios()) {

            if (scenario.criteriosAceitacao() == null) {
                throw new IllegalArgumentException(
                        "A lista de critérios do cenário "
                                + scenario.id()
                                + " não pode ser nula."
                );
            }

            for (String criterioId : scenario.criteriosAceitacao()) {

                if (!criteriosAceitacao.contains(criterioId)) {
                    throw new IllegalArgumentException(
                            "O cenário "
                                    + scenario.id()
                                    + " referencia o critério inexistente: "
                                    + criterioId
                    );
                }
            }

            if (scenario.regrasNegocio() == null) {
                throw new IllegalArgumentException(
                        "A lista de regras do cenário "
                                + scenario.id()
                                + " não pode ser nula."
                );
            }

            for (String regraId : scenario.regrasNegocio()) {

                if (!regrasNegocio.contains(regraId)) {
                    throw new IllegalArgumentException(
                            "O cenário "
                                    + scenario.id()
                                    + " referencia a regra de negócio inexistente: "
                                    + regraId
                    );
                }
            }

            if (scenario.riscos() == null) {
                throw new IllegalArgumentException(
                        "A lista de riscos do cenário "
                                + scenario.id()
                                + " não pode ser nula."
                );
            }

            for (String riscoId : scenario.riscos()) {

                if (!riscos.contains(riscoId)) {
                    throw new IllegalArgumentException(
                            "O cenário "
                                    + scenario.id()
                                    + " referencia o risco inexistente: "
                                    + riscoId
                    );
                }
            }
        }

        for (TestCase testCase : response.casosDeTeste()) {

            if (!cenarios.contains(testCase.scenarioId())) {
                throw new IllegalArgumentException(
                        "O caso de teste "
                                + testCase.id()
                                + " referencia o cenário inexistente: "
                                + testCase.scenarioId()
                );
            }
        }
    }

    private void validarResumo(String resumo) {

        if (isBlank(resumo)) {
            throw new IllegalArgumentException(
                    "O resumo da análise não pode estar vazio."
            );
        }
    }

    private void validarRiscos(List<Risk> riscos) {

        if (riscos == null) {
            throw new IllegalArgumentException(
                    "A lista de riscos não pode ser nula."
            );
        }

        Set<String> ids = new HashSet<>();

        for (Risk risk : riscos) {

            if (risk == null) {
                throw new IllegalArgumentException(
                        "O risco não pode ser nulo."
                );
            }

            validarCampo(risk.id(), "ID do risco");
            validarCampo(risk.descricao(), "Descrição do risco");
            validarCampo(risk.impacto(), "Impacto");
            validarCampo(risk.probabilidade(), "Probabilidade");
            validarCampo(risk.prioridade(), "Prioridade do risco");
            validarCampo(risk.justificativa(), "Justificativa");

            if (!ids.add(risk.id())) {
                throw new IllegalArgumentException(
                        "ID de risco duplicado: " + risk.id()
                );
            }
        }
    }

    private void validarCenarios(List<TestScenario> cenarios) {

        if (cenarios == null) {
            throw new IllegalArgumentException(
                    "A lista de cenários não pode ser nula."
            );
        }

        Set<String> ids = new HashSet<>();

        for (TestScenario scenario : cenarios) {

            if (scenario == null) {
                throw new IllegalArgumentException(
                        "O cenário de teste não pode ser nulo."
                );
            }

            validarCampo(scenario.id(), "ID do cenário");
            validarCampo(scenario.descricao(), "Descrição do cenário");
            validarCampo(scenario.tipo(), "Tipo do cenário");
            validarCampo(scenario.prioridade(), "Prioridade do cenário");

            if (!ids.add(scenario.id())) {
                throw new IllegalArgumentException(
                        "ID de cenário duplicado: " + scenario.id()
                );
            }
        }
    }

    private void validarCasosDeTeste(List<TestCase> casosDeTeste) {

        if (casosDeTeste == null || casosDeTeste.isEmpty()) {
            throw new IllegalArgumentException(
                    "A análise deve possuir pelo menos um caso de teste."
            );
        }

        Set<String> ids = new HashSet<>();

        for (TestCase testCase : casosDeTeste) {

            if (testCase == null) {
                throw new IllegalArgumentException(
                        "O caso de teste não pode ser nulo."
                );
            }

            validarCampo(testCase.id(), "ID do caso de teste");
            validarCampo(testCase.scenarioId(), "ID do cenário");
            validarCampo(testCase.descricao(), "Descrição do caso");
            validarCampo(testCase.dado(), "Dado");
            validarCampo(testCase.quando(), "Quando");
            validarCampo(testCase.entao(), "Então");

            validarTipo(testCase.tipo());
            validarPrioridade(testCase.prioridade());

            if (!ids.add(testCase.id())) {
                throw new IllegalArgumentException(
                        "ID de caso de teste duplicado: " + testCase.id()
                );
            }
        }
    }

    private void validarTipo(String tipo) {

        validarCampo(tipo, "Tipo");

        if (!TIPOS_VALIDOS.contains(tipo)) {
            throw new IllegalArgumentException(
                    "Tipo de teste inválido: " + tipo
                            + ". Valores permitidos: " + TIPOS_VALIDOS
            );
        }
    }

    private void validarPrioridade(String prioridade) {

        validarCampo(prioridade, "Prioridade");

        if (!PRIORIDADES_VALIDAS.contains(prioridade)) {
            throw new IllegalArgumentException(
                    "Prioridade inválida: " + prioridade
                            + ". Valores permitidos: " + PRIORIDADES_VALIDAS
            );
        }
    }

    private void validarCampo(String valor, String nomeCampo) {

        if (isBlank(valor)) {
            throw new IllegalArgumentException(
                    "O campo '" + nomeCampo + "' não pode estar vazio."
            );
        }
    }

    private boolean isBlank(String valor) {
        return valor == null || valor.isBlank();
    }
}