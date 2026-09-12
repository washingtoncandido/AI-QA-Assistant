package br.com.qa.ai.config;

import com.google.genai.types.Schema;
import com.google.genai.types.Type;

import java.util.List;
import java.util.Map;

public class TestCaseSchema {

    private TestCaseSchema() {
    }

    public static Schema create() {

        Schema testCaseSchema = Schema.builder()
                .type(Type.Known.OBJECT)
                .properties(Map.of(
                        "id", Schema.builder()
                                .type(Type.Known.STRING)
                                .build(),

                        "cenario", Schema.builder()
                                .type(Type.Known.STRING)
                                .build(),

                        "tipo", Schema.builder()
                                .type(Type.Known.STRING)
                                .build(),

                        "prioridade", Schema.builder()
                                .type(Type.Known.STRING)
                                .build(),

                        "dado", Schema.builder()
                                .type(Type.Known.STRING)
                                .build(),

                        "quando", Schema.builder()
                                .type(Type.Known.STRING)
                                .build(),

                        "entao", Schema.builder()
                                .type(Type.Known.STRING)
                                .build()
                ))
                .required(List.of(
                        "id",
                        "cenario",
                        "tipo",
                        "prioridade",
                        "dado",
                        "quando",
                        "entao"
                ))
                .build();

        return Schema.builder()
                .type(Type.Known.OBJECT)
                .properties(Map.of(
                        "funcionalidade", Schema.builder()
                                .type(Type.Known.STRING)
                                .build(),

                        "casos", Schema.builder()
                                .type(Type.Known.ARRAY)
                                .items(testCaseSchema)
                                .build()
                ))
                .required(List.of(
                        "funcionalidade",
                        "casos"
                ))
                .build();
    }
}