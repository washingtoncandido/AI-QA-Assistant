package br.com.qa.ai.client;

import br.com.qa.ai.config.GeminiConfig;
import br.com.qa.ai.schema.TestCaseSchema;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;

public class GeminiClient {

    private static final int MAX_RETRIES = 3;
    private static final long INITIAL_DELAY_MS = 2000;

    private final Client client;

    public GeminiClient() {
        this.client = Client.builder()
                .apiKey(GeminiConfig.getApiKey())
                .build();
    }

    public String generate(String prompt) {

        GenerateContentConfig config =
                GenerateContentConfig.builder()
                        .responseMimeType("application/json")
                        .candidateCount(1)
                        .responseSchema(TestCaseSchema.create())
                        .build();

        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {

            try {

                GenerateContentResponse response =
                        client.models.generateContent(
                                GeminiConfig.getModel(),
                                prompt,
                                config
                        );

                return response.text();

            } catch (Exception e) {

                System.out.println(
                        "Tentativa " + attempt +
                                " falhou: " + e.getMessage()
                );

                if (attempt == MAX_RETRIES) {
                    throw new RuntimeException(
                            "Não foi possível obter resposta do Gemini após "
                                    + MAX_RETRIES + " tentativas.",
                            e
                    );
                }

                long delay =
                        INITIAL_DELAY_MS *
                                (long) Math.pow(2, attempt - 1);

                System.out.println(
                        "Aguardando "
                                + delay
                                + " ms antes de tentar novamente..."
                );

                try {

                    Thread.sleep(delay);

                } catch (InterruptedException interruptedException) {

                    Thread.currentThread().interrupt();

                    throw new RuntimeException(
                            "Thread interrompida durante o retry.",
                            interruptedException
                    );
                }
            }
        }

        throw new IllegalStateException(
                "Fluxo de retry inválido."
        );
    }
}