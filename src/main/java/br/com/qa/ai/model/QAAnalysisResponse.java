package br.com.qa.ai.model;

import java.util.List;

public record QAAnalysisResponse(
        String resumo,
        List<Risk> riscos,
        List<TestScenario> cenarios,
        List<TestCase> casosDeTeste
) {
}