package br.com.qa.ai.parser;

import br.com.qa.ai.model.QAAnalysisResponse;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestCaseParser {

    private final ObjectMapper objectMapper;

    public TestCaseParser() {

        this.objectMapper = new ObjectMapper();

        this.objectMapper.configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                false
        );
    }

    public QAAnalysisResponse parse(String json) {

        try {

            return objectMapper.readValue(
                    json,
                    QAAnalysisResponse.class
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Erro ao converter resposta da IA para QAAnalysisResponse.",
                    e
            );
        }
    }
}