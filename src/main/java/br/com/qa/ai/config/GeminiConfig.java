package br.com.qa.ai.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class GeminiConfig {

    private static final Properties PROPERTIES =
            new Properties();

    static {

        try (InputStream input =
                     GeminiConfig.class
                             .getClassLoader()
                             .getResourceAsStream(
                                     "application.properties"
                             )) {

            if (input == null) {
                throw new IllegalStateException(
                        "Arquivo application.properties não encontrado."
                );
            }

            PROPERTIES.load(input);

        } catch (IOException e) {

            throw new IllegalStateException(
                    "Não foi possível carregar o application.properties.",
                    e
            );
        }
    }

    private GeminiConfig() {
    }

    public static String getApiKey() {

        String apiKey =
                PROPERTIES.getProperty("gemini.api.key");

        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(
                    "A propriedade gemini.api.key não foi configurada."
            );
        }

        return apiKey;
    }

    public static String getModel() {

        String model =
                PROPERTIES.getProperty("gemini.model");

        if (model == null || model.isBlank()) {
            throw new IllegalStateException(
                    "A propriedade gemini.model não foi configurada."
            );
        }

        return model;
    }
}