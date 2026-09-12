package br.com.qa.ai.model;

import java.util.List;

public record BusinessRule(
        String id,
        String titulo,
        String funcionalidade,
        String regra,
        String condicao,
        String comportamentoEsperado,
        String excecoes,
        List<String> termosRelacionados
) {
}