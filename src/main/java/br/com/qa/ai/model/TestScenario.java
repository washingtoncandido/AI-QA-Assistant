package br.com.qa.ai.model;

import java.util.List;

public record TestScenario(
        String id,
        String descricao,
        String tipo,
        String prioridade,
        List<String> criteriosAceitacao,
        List<String> regrasNegocio,
        List<String> riscos
) {
}