package br.com.qa.ai.model;

public record Risk(
        String id,
        String descricao,
        String impacto,
        String probabilidade,
        String prioridade,
        String justificativa
) {
}