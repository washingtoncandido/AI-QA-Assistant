package br.com.qa.ai.model;

public record TestCase(
        String id,
        String scenarioId,
        String descricao,
        String tipo,
        String prioridade,
        String dado,
        String quando,
        String entao
) {
}