package br.com.qa.ai;

import br.com.qa.ai.client.KnowledgeGeminiClient;
import br.com.qa.ai.model.KnowledgeQuery;
import br.com.qa.ai.service.KnowledgeSearchService;
import br.com.qa.ai.service.KnowledgeService;

import java.util.Scanner;

public class KnowledgeAssistantMain {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("ASSISTENTE DE QA");
        System.out.println("======================================");

        Scanner scanner = new Scanner(System.in);

        System.out.print("\nFaça sua pergunta: ");

        String pergunta = scanner.nextLine();

        KnowledgeQuery query =
                new KnowledgeQuery(pergunta);

        KnowledgeGeminiClient geminiClient =
                new KnowledgeGeminiClient();

        KnowledgeSearchService searchService =
                new KnowledgeSearchService();

        KnowledgeService knowledgeService =
                new KnowledgeService(
                        geminiClient,
                        searchService
                );

        String resposta =
                knowledgeService.ask(query);

        System.out.println("\nPERGUNTA:");
        System.out.println(pergunta);

        System.out.println("\nRESPOSTA:");
        System.out.println(resposta);

        System.out.println("\n======================================");
    }
}