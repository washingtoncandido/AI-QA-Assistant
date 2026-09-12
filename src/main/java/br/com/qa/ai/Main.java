package br.com.qa.ai;

import br.com.qa.ai.client.GeminiClient;
import br.com.qa.ai.knowledge.BusinessRuleRepository;
import br.com.qa.ai.model.AcceptanceCriterion;
import br.com.qa.ai.model.BusinessRule;
import br.com.qa.ai.model.Context;
import br.com.qa.ai.model.QAAnalysisResponse;
import br.com.qa.ai.model.QARequest;
import br.com.qa.ai.model.UserStory;
import br.com.qa.ai.parser.TestCaseParser;
import br.com.qa.ai.service.QAAnalysisService;
import br.com.qa.ai.validator.QAAnalysisValidator;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        Context context = new Context(
                "Impressoras 3D",
                "AI-QA-Assistant",
                "Cadastro de conta",
                "Web",
                "TST",
                "Análise inteligente de qualidade"
        );

        UserStory userStory = new UserStory(
                "Cadastro de conta",
                "um usuário autenticado",
                "criar uma conta",
                "poder utilizar a conta no sistema",
                List.of(
                        new AcceptanceCriterion(
                                "CA001",
                                "O usuário autenticado deve conseguir cadastrar uma conta."
                        ),
                        new AcceptanceCriterion(
                                "CA002",
                                "O nome da conta deve ser informado."
                        ),
                        new AcceptanceCriterion(
                                "CA003",
                                "O sistema deve impedir o cadastro quando o nome possuir apenas espaços."
                        )
                )
        );

        BusinessRuleRepository repository =
                new BusinessRuleRepository();

        List<BusinessRule> businessRules =
                repository.load("cadastro.md");

        QARequest request = new QARequest(
                context,
                userStory,
                businessRules
        );

        GeminiClient geminiClient =
                new GeminiClient();

        TestCaseParser parser =
                new TestCaseParser();

        QAAnalysisValidator validator =
                new QAAnalysisValidator();

        QAAnalysisService service =
                new QAAnalysisService(
                        geminiClient,
                        parser,
                        validator
                );

        QAAnalysisResponse response =
                service.analyze(request);

        System.out.println("======================================");
        System.out.println("ANÁLISE DE QA");
        System.out.println("======================================");

        System.out.println("\nRESUMO:");
        System.out.println(response.resumo());

        System.out.println("\nRISCOS:");

        response.riscos().forEach(risk -> {

            System.out.println(
                    risk.id()
                            + " - "
                            + risk.descricao()
                            + " | Impacto: "
                            + risk.impacto()
                            + " | Probabilidade: "
                            + risk.probabilidade()
                            + " | Prioridade: "
                            + risk.prioridade()
            );
        });

        System.out.println("\nCENÁRIOS:");

        response.cenarios().forEach(scenario -> {

            System.out.println(
                    scenario.id()
                            + " - "
                            + scenario.descricao()
                            + " | Tipo: "
                            + scenario.tipo()
                            + " | Prioridade: "
                            + scenario.prioridade()
            );

            System.out.println(
                    "Critérios: "
                            + scenario.criteriosAceitacao()
            );

            System.out.println(
                    "Regras: "
                            + scenario.regrasNegocio()
            );

            System.out.println(
                    "Riscos: "
                            + scenario.riscos()
            );
        });

        System.out.println("\nCASOS DE TESTE:");

        response.casosDeTeste().forEach(testCase -> {

            System.out.println(
                    "\n"
                            + testCase.id()
                            + " - "
                            + testCase.descricao()
            );

            System.out.println(
                    "Cenário: "
                            + testCase.scenarioId()
            );

            System.out.println(
                    "Tipo: "
                            + testCase.tipo()
            );

            System.out.println(
                    "Prioridade: "
                            + testCase.prioridade()
            );

            System.out.println(
                    "Dado: "
                            + testCase.dado()
            );

            System.out.println(
                    "Quando: "
                            + testCase.quando()
            );

            System.out.println(
                    "Então: "
                            + testCase.entao()
            );
        });

        System.out.println("\n======================================");
        System.out.println("Análise validada com sucesso.");
        System.out.println("======================================");
    }
}