package br.com.qa.ai.knowledge;

import br.com.qa.ai.model.BusinessRule;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class BusinessRuleRepository {

    public List<BusinessRule> load(String fileName) {

        String content = loadFile(fileName);

        return parse(content);
    }

    public List<BusinessRule> loadAll() {

        List<BusinessRule> rules = new ArrayList<>();

        try {

            var resource =
                    getClass()
                            .getClassLoader()
                            .getResource("business-rules");

            if (resource == null) {
                throw new IllegalArgumentException(
                        "Diretório de regras de negócio não encontrado."
                );
            }

            var directory =
                    new java.io.File(resource.toURI());

            var files =
                    directory.listFiles(
                            (dir, name) ->
                                    name.toLowerCase().endsWith(".md")
                    );

            if (files == null) {
                return rules;
            }

            for (var file : files) {

                rules.addAll(
                        load(file.getName())
                );
            }

            return rules;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Erro ao carregar todas as regras de negócio.",
                    e
            );
        }
    }

    private String loadFile(String fileName) {

        String path = "business-rules/" + fileName;

        try (InputStream inputStream =
                     getClass()
                             .getClassLoader()
                             .getResourceAsStream(path)) {

            if (inputStream == null) {
                throw new IllegalArgumentException(
                        "Regra de negócio não encontrada: " + path
                );
            }

            return new String(
                    inputStream.readAllBytes(),
                    StandardCharsets.UTF_8
            );

        } catch (IOException e) {

            throw new RuntimeException(
                    "Erro ao ler regras de negócio: " + path,
                    e
            );
        }
    }

    private List<BusinessRule> parse(String content) {

        List<BusinessRule> rules = new ArrayList<>();

        String[] lines = content.split("\\R");

        String currentId = null;
        String currentTitle = null;
        String currentField = null;

        String funcionalidade = null;
        String regra = null;
        String preCondicoes = null;
        String condicao = null;
        String comportamentoEsperado = null;
        String excecoes = null;

        List<String> termosRelacionados = new ArrayList<>();

        for (String line : lines) {

            line = line.trim();

            /*
             * Identifica uma nova regra de negócio.
             *
             * Exemplo:
             *
             * ## RN001 - Nome obrigatório
             */
            if (line.startsWith("## RN")) {

                /*
                 * Antes de iniciar uma nova regra,
                 * salva a regra anterior.
                 */
                if (currentId != null) {

                    rules.add(
                            createBusinessRule(
                                    currentId,
                                    currentTitle,
                                    funcionalidade,
                                    regra,
                                    preCondicoes,
                                    condicao,
                                    comportamentoEsperado,
                                    excecoes,
                                    termosRelacionados
                            )
                    );
                }

                /*
                 * Remove "## " do início.
                 *
                 * Resultado:
                 * RN001 - Nome obrigatório
                 */
                String header =
                        line.substring(3).trim();

                int separatorIndex =
                        header.indexOf(" - ");

                if (separatorIndex == -1) {

                    throw new IllegalArgumentException(
                            "Formato inválido de regra de negócio: "
                                    + line
                    );
                }

                /*
                 * Extrai o ID.
                 *
                 * RN001
                 */
                currentId =
                        header.substring(
                                0,
                                separatorIndex
                        ).trim();

                /*
                 * Extrai o título.
                 *
                 * Nome obrigatório
                 */
                currentTitle =
                        header.substring(
                                separatorIndex + 3
                        ).trim();

                /*
                 * Reseta os campos da nova regra.
                 */
                funcionalidade = null;
                regra = null;
                preCondicoes = null;
                condicao = null;
                comportamentoEsperado = null;
                excecoes = null;
                termosRelacionados = new ArrayList<>();

                currentField = null;

                continue;
            }

            /*
             * Identifica os campos da regra.
             *
             * Exemplo:
             *
             * ### Funcionalidade
             * ### Regra
             * ### Condição
             * ### Comportamento esperado
             */
            if (line.startsWith("### ")) {

                currentField =
                        normalizeField(
                                line.substring(4).trim()
                        );

                continue;
            }

            /*
             * Ignora conteúdo antes da primeira regra.
             *
             * Exemplo:
             *
             * # Regras de Negócio - Cadastro
             */
            if (currentId == null || line.isBlank()) {
                continue;
            }

            /*
             * Evita processamento quando não existe
             * nenhum campo identificado.
             */
            if (currentField == null) {
                continue;
            }

            /*
             * Trata os termos relacionados como uma lista.
             *
             * Exemplo:
             *
             * cadastro, nome, usuário
             */
            if ("termosRelacionados".equals(currentField)) {

                for (String termo : line.split(",")) {

                    String value = termo.trim();

                    if (!value.isBlank()) {
                        termosRelacionados.add(value);
                    }
                }

                continue;
            }

            /*
             * Preenche o campo correspondente.
             */
            switch (currentField) {

                case "funcionalidade" ->
                        funcionalidade =
                                appendText(
                                        funcionalidade,
                                        line
                                );

                case "regra" ->
                        regra =
                                appendText(
                                        regra,
                                        line
                                );

                case "preCondicoes" ->
                        preCondicoes =
                                appendText(
                                        preCondicoes,
                                        line
                                );

                case "condicao" ->
                        condicao =
                                appendText(
                                        condicao,
                                        line
                                );

                case "comportamentoEsperado" ->
                        comportamentoEsperado =
                                appendText(
                                        comportamentoEsperado,
                                        line
                                );

                case "excecoes" ->
                        excecoes =
                                appendText(
                                        excecoes,
                                        line
                                );

                default -> {
                    // Ignora campos desconhecidos.
                }
            }
        }

        /*
         * Adiciona a última regra encontrada.
         */
        if (currentId != null) {

            rules.add(
                    createBusinessRule(
                            currentId,
                            currentTitle,
                            funcionalidade,
                            regra,
                            preCondicoes,
                            condicao,
                            comportamentoEsperado,
                            excecoes,
                            termosRelacionados
                    )
            );
        }

        return rules;
    }

    private BusinessRule createBusinessRule(
            String id,
            String titulo,
            String funcionalidade,
            String regra,
            String preCondicoes,
            String condicao,
            String comportamentoEsperado,
            String excecoes,
            List<String> termosRelacionados) {

        return new BusinessRule(
                id,
                titulo,
                funcionalidade,
                regra,
                condicao,
                comportamentoEsperado,
                excecoes,
                List.copyOf(termosRelacionados)
        );
    }

    private String normalizeField(String field) {

        return switch (field) {

            case "Funcionalidade" ->
                    "funcionalidade";

            case "Regra" ->
                    "regra";

            case "Pré-condições" ->
                    "preCondicoes";

            case "Condição" ->
                    "condicao";

            case "Comportamento esperado" ->
                    "comportamentoEsperado";

            case "Exceções" ->
                    "excecoes";

            case "Termos relacionados" ->
                    "termosRelacionados";

            default ->
                    field;
        };
    }

    private String appendText(
            String current,
            String value) {

        if (current == null || current.isBlank()) {
            return value;
        }

        return current + " " + value;
    }
}