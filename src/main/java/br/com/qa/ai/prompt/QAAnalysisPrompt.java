package br.com.qa.ai.prompt;

import br.com.qa.ai.model.AcceptanceCriterion;
import br.com.qa.ai.model.BusinessRule;
import br.com.qa.ai.model.QARequest;

public class QAAnalysisPrompt {

    private QAAnalysisPrompt() {
    }

    public static String build(QARequest request) {

        String criteriosAceitacao = request.userStory()
                .acceptanceCriteria()
                .stream()
                .map(QAAnalysisPrompt::formatCriterion)
                .reduce((a, b) -> a + "\n" + b)
                .orElse("Nenhum critério de aceitação informado.");

        String regrasNegocio = request.businessRules()
                .stream()
                .map(QAAnalysisPrompt::formatBusinessRule)
                .reduce((a, b) -> a + "\n" + b)
                .orElse("Nenhuma regra de negócio informada.");

        return """
                Você é um especialista em Quality Assurance.
                
                Analise o contexto, o requisito, os critérios de aceitação
                e as regras de negócio fornecidas.
                
                Sua análise deve:
                
                1. Resumir o requisito.
                2. Identificar riscos de qualidade.
                3. Criar cenários de teste.
                4. Criar casos de teste para os cenários identificados.
                5. Manter rastreabilidade entre critérios, regras, riscos,
                   cenários e casos de teste.
                
                REGRAS IMPORTANTES:
                
                - Não invente requisitos.
                - Não invente regras de negócio.
                - Utilize somente os IDs de regras fornecidos.
                - Utilize somente os IDs de critérios fornecidos.
                - Cada risco deve possuir um ID único.
                - Cada cenário deve possuir um ID único.
                - Cada caso de teste deve possuir um ID único.
                - Um cenário pode estar relacionado a várias regras ou riscos.
                - Um caso de teste deve informar o ID do cenário relacionado.
                - Cubra cenários positivos, negativos e de borda quando aplicável.
                - Não crie testes sem relação com o requisito.
                - Caso não exista relação com uma regra ou risco, utilize lista vazia.
                
                TIPOS DE TESTE PERMITIDOS:
                
                - Positivo
                - Negativo
                - Borda
                
                PRIORIDADES PERMITIDAS:
                
                - Alta
                - Média
                - Baixa
                
                A resposta deve ser exclusivamente um JSON válido.
                
                Estrutura obrigatória:
                
                {
                  "resumo": "resumo do requisito",
                
                  "riscos": [
                    {
                      "id": "R001",
                      "descricao": "descrição do risco",
                      "impacto": "Alto",
                      "probabilidade": "Média",
                      "prioridade": "Alta",
                      "justificativa": "justificativa do risco"
                    }
                  ],
                
                  "cenarios": [
                    {
                      "id": "TS001",
                      "descricao": "descrição do cenário",
                      "tipo": "Positivo",
                      "prioridade": "Alta",
                      "criteriosAceitacao": ["CA001"],
                      "regrasNegocio": ["RN001"],
                      "riscos": ["R001"]
                    }
                  ],
                
                  "casosDeTeste": [
                    {
                      "id": "CT001",
                      "scenarioId": "TS001",
                      "descricao": "descrição do caso de teste",
                      "tipo": "Positivo",
                      "prioridade": "Alta",
                      "dado": "pré-condição",
                      "quando": "ação executada",
                      "entao": "resultado esperado"
                    }
                  ]
                }
                
                CONTEXTO:
                
                Domínio: %s
                Projeto: %s
                Funcionalidade: %s
                Plataforma: %s
                Ambiente: %s
                Observações: %s
                
                REQUISITO:
                
                Título: %s
                Como: %s
                Quero: %s
                Para: %s
                
                CRITÉRIOS DE ACEITAÇÃO:
                
                %s
                
                REGRAS DE NEGÓCIO:
                
                %s
                """.formatted(
                request.context().dominio(),
                request.context().projeto(),
                request.context().funcionalidade(),
                request.context().plataforma(),
                request.context().ambiente(),
                request.context().observacoes(),
                request.userStory().titulo(),
                request.userStory().como(),
                request.userStory().quero(),
                request.userStory().para(),
                criteriosAceitacao,
                regrasNegocio
        );
    }

    private static String formatCriterion(
            AcceptanceCriterion criterion) {

        return "- " + criterion.id()
                + ": " + criterion.descricao();
    }

    private static String formatBusinessRule(
            BusinessRule rule) {

        return """
                - ID: %s
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
        );
    }
}