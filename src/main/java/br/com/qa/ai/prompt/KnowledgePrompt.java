package br.com.qa.ai.prompt;

import br.com.qa.ai.model.BusinessRule;
import br.com.qa.ai.model.KnowledgeQuery;

import java.util.List;

public final class KnowledgePrompt {

    private KnowledgePrompt() {
    }

    public static String build(
            KnowledgeQuery query,
            List<BusinessRule> rules) {

        StringBuilder context = new StringBuilder();

        for (BusinessRule rule : rules) {

            context.append("""
        ID: %s
        Título: %s
        Funcionalidade: %s
        Regra: %s
        Condição: %s
        Comportamento esperado: %s
        Exceções: %s
        Termos relacionados: %s

        """.formatted(
                    rule.id(),
                    rule.titulo(),
                    rule.funcionalidade(),
                    rule.regra(),
                    rule.condicao(),
                    rule.comportamentoEsperado(),
                    rule.excecoes(),
                    String.join(
                            ", ",
                            rule.termosRelacionados()
                    )
            ));
        }

        if (context.isEmpty()) {

            context.append(
                    "Nenhuma regra de negócio relevante foi encontrada."
            );
        }

        return """
                Você é um assistente especialista em Quality Assurance.

                Responda à pergunta do usuário utilizando SOMENTE
                as informações disponíveis na base de conhecimento.

                REGRAS IMPORTANTES:

                - Não invente informações.
                - Não crie regras de negócio que não estejam na base.
                - Quando uma regra for utilizada na resposta,
                  informe seu ID.
                - Seja claro e objetivo.
                - Se a informação não estiver disponível,
                  informe que ela não está disponível na base de conhecimento.
                - Diferencie claramente fatos encontrados na base
                  de qualquer interpretação.
                - Responda em português.

                PERGUNTA DO USUÁRIO:

                %s

                REGRAS DE NEGÓCIO ENCONTRADAS:

                %s
                """.formatted(
                query.pergunta(),
                context
        );
    }
}