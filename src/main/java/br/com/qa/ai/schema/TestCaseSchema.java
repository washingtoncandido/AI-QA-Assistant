package br.com.qa.ai.schema;

import com.google.common.collect.ImmutableMap;
import com.google.genai.types.Schema;
import com.google.genai.types.Type;

public final class TestCaseSchema {

    private TestCaseSchema() {
    }

    public static Schema create() {

        Schema riskSchema = Schema.builder()
                .type(Type.Known.OBJECT)
                .properties(
                        ImmutableMap.of(
                                "id",
                                Schema.builder()
                                        .type(Type.Known.STRING)
                                        .build(),

                                "descricao",
                                Schema.builder()
                                        .type(Type.Known.STRING)
                                        .build(),

                                "impacto",
                                Schema.builder()
                                        .type(Type.Known.STRING)
                                        .build(),

                                "probabilidade",
                                Schema.builder()
                                        .type(Type.Known.STRING)
                                        .build(),

                                "prioridade",
                                Schema.builder()
                                        .type(Type.Known.STRING)
                                        .build(),

                                "justificativa",
                                Schema.builder()
                                        .type(Type.Known.STRING)
                                        .build()
                        )
                )
                .required(
                        "id",
                        "descricao",
                        "impacto",
                        "probabilidade",
                        "prioridade",
                        "justificativa"
                )
                .build();

        Schema scenarioSchema = Schema.builder()
                .type(Type.Known.OBJECT)
                .properties(
                        ImmutableMap.of(
                                "id",
                                Schema.builder()
                                        .type(Type.Known.STRING)
                                        .build(),

                                "descricao",
                                Schema.builder()
                                        .type(Type.Known.STRING)
                                        .build(),

                                "tipo",
                                Schema.builder()
                                        .type(Type.Known.STRING)
                                        .build(),

                                "prioridade",
                                Schema.builder()
                                        .type(Type.Known.STRING)
                                        .build(),

                                "criteriosAceitacao",
                                Schema.builder()
                                        .type(Type.Known.ARRAY)
                                        .items(
                                                Schema.builder()
                                                        .type(Type.Known.STRING)
                                                        .build()
                                        )
                                        .build(),

                                "regrasNegocio",
                                Schema.builder()
                                        .type(Type.Known.ARRAY)
                                        .items(
                                                Schema.builder()
                                                        .type(Type.Known.STRING)
                                                        .build()
                                        )
                                        .build(),

                                "riscos",
                                Schema.builder()
                                        .type(Type.Known.ARRAY)
                                        .items(
                                                Schema.builder()
                                                        .type(Type.Known.STRING)
                                                        .build()
                                        )
                                        .build()
                        )
                )
                .required(
                        "id",
                        "descricao",
                        "tipo",
                        "prioridade",
                        "criteriosAceitacao",
                        "regrasNegocio",
                        "riscos"
                )
                .build();

        Schema testCaseSchema = Schema.builder()
                .type(Type.Known.OBJECT)
                .properties(
                        ImmutableMap.of(
                                "id",
                                Schema.builder()
                                        .type(Type.Known.STRING)
                                        .build(),

                                "scenarioId",
                                Schema.builder()
                                        .type(Type.Known.STRING)
                                        .build(),

                                "descricao",
                                Schema.builder()
                                        .type(Type.Known.STRING)
                                        .build(),

                                "tipo",
                                Schema.builder()
                                        .type(Type.Known.STRING)
                                        .build(),

                                "prioridade",
                                Schema.builder()
                                        .type(Type.Known.STRING)
                                        .build(),

                                "dado",
                                Schema.builder()
                                        .type(Type.Known.STRING)
                                        .build(),

                                "quando",
                                Schema.builder()
                                        .type(Type.Known.STRING)
                                        .build(),

                                "entao",
                                Schema.builder()
                                        .type(Type.Known.STRING)
                                        .build()
                        )
                )
                .required(
                        "id",
                        "scenarioId",
                        "descricao",
                        "tipo",
                        "prioridade",
                        "dado",
                        "quando",
                        "entao"
                )
                .build();

        return Schema.builder()
                .type(Type.Known.OBJECT)
                .properties(
                        ImmutableMap.of(

                                "resumo",
                                Schema.builder()
                                        .type(Type.Known.STRING)
                                        .build(),

                                "riscos",
                                Schema.builder()
                                        .type(Type.Known.ARRAY)
                                        .items(riskSchema)
                                        .build(),

                                "cenarios",
                                Schema.builder()
                                        .type(Type.Known.ARRAY)
                                        .items(scenarioSchema)
                                        .build(),

                                "casosDeTeste",
                                Schema.builder()
                                        .type(Type.Known.ARRAY)
                                        .items(testCaseSchema)
                                        .build()
                        )
                )
                .required(
                        "resumo",
                        "riscos",
                        "cenarios",
                        "casosDeTeste"
                )
                .build();
    }
}