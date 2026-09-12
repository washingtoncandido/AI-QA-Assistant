package br.com.qa.ai.service;

import br.com.qa.ai.knowledge.BusinessRuleRepository;
import br.com.qa.ai.model.BusinessRule;

import java.util.List;

public class KnowledgeSearchService {

    private final BusinessRuleRepository repository;

    public KnowledgeSearchService() {
        this.repository =
                new BusinessRuleRepository();
    }

    public List<BusinessRule> search(String question) {

        if (question == null || question.isBlank()) {
            throw new IllegalArgumentException(
                    "A pergunta não pode estar vazia."
            );
        }

        List<BusinessRule> rules =
                repository.loadAll();

        String normalizedQuestion =
                question.toLowerCase();

        return rules.stream()
                .filter(rule ->
                        containsRelevantInformation(
                                rule,
                                normalizedQuestion
                        )
                )
                .toList();
    }

    private boolean containsRelevantInformation(
            BusinessRule rule,
            String question) {

        String content =
                (
                        rule.id()
                                + " "
                                + rule.titulo()
                                + " "
                                + rule.funcionalidade()
                                + " "
                                + rule.regra()
                                + " "
                                + rule.condicao()
                                + " "
                                + rule.comportamentoEsperado()
                                + " "
                                + rule.excecoes()
                                + " "
                                + String.join(
                                " ",
                                rule.termosRelacionados()
                        )
                ).toLowerCase();

        String[] words =
                question.split("\\s+");

        for (String word : words) {

            if (word.length() < 3) {
                continue;
            }

            if (content.contains(word)) {
                return true;
            }
        }

        return false;
    }
}