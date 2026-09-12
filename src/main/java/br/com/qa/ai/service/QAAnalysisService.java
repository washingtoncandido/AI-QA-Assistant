package br.com.qa.ai.service;

import br.com.qa.ai.client.GeminiClient;
import br.com.qa.ai.model.QAAnalysisResponse;
import br.com.qa.ai.model.QARequest;
import br.com.qa.ai.parser.TestCaseParser;
import br.com.qa.ai.prompt.QAAnalysisPrompt;
import br.com.qa.ai.validator.QAAnalysisValidator;

public class QAAnalysisService {

    private final GeminiClient geminiClient;
    private final TestCaseParser parser;
    private final QAAnalysisValidator validator;

    public QAAnalysisService(
            GeminiClient geminiClient,
            TestCaseParser parser,
            QAAnalysisValidator validator) {

        this.geminiClient = geminiClient;
        this.parser = parser;
        this.validator = validator;
    }

    public QAAnalysisResponse analyze(QARequest request) {

        validateRequest(request);

        String prompt = QAAnalysisPrompt.build(request);

        String jsonResponse = geminiClient.generate(prompt);

        QAAnalysisResponse response = parser.parse(jsonResponse);

        validator.validate(response, request);

        return response;
    }

    private void validateRequest(QARequest request) {

        if (request == null) {
            throw new IllegalArgumentException(
                    "A solicitação de análise de QA não pode ser nula."
            );
        }

        if (request.context() == null) {
            throw new IllegalArgumentException(
                    "O contexto da análise não pode ser nulo."
            );
        }

        if (request.userStory() == null) {
            throw new IllegalArgumentException(
                    "A User Story não pode ser nula."
            );
        }

        if (request.businessRules() == null) {
            throw new IllegalArgumentException(
                    "A lista de regras de negócio não pode ser nula."
            );
        }
    }
}