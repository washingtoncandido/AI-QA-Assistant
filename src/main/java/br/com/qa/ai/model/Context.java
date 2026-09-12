package br.com.qa.ai.model;

public record Context(
        String dominio,
        String projeto,
        String funcionalidade,
        String plataforma,
        String ambiente,
        String observacoes
) {
}