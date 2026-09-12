package br.com.qa.ai.service;

import br.com.qa.ai.client.KnowledgeGeminiClient;
import br.com.qa.ai.service.KnowledgeSearchService;
import br.com.qa.ai.model.BusinessRule;
import br.com.qa.ai.model.KnowledgeQuery;
import br.com.qa.ai.prompt.KnowledgePrompt;

import java.util.List;

public class KnowledgeService {

    private final KnowledgeGeminiClient geminiClient;
    private final KnowledgeSearchService searchService;

    public KnowledgeService(
            KnowledgeGeminiClient geminiClient,
            KnowledgeSearchService searchService) {

        this.geminiClient = geminiClient;
        this.searchService = searchService;
    }

    public String ask(KnowledgeQuery query) {

        validateQuery(query);

        List<BusinessRule> rules =
                searchService.search(
                        query.pergunta()
                );

        String prompt =
                KnowledgePrompt.build(
                        query,
                        rules
                );

        return geminiClient.generate(prompt);
    }

    private void validateQuery(KnowledgeQuery query) {

        if (query == null) {
            throw new IllegalArgumentException(
                    "A consulta de conhecimento não pode ser nula."
            );
        }

        if (query.pergunta() == null
                || query.pergunta().isBlank()) {

            throw new IllegalArgumentException(
                    "A pergunta não pode estar vazia."
            );
        }
    }
}