package br.com.qa.ai;

import br.com.qa.ai.knowledge.BusinessRuleRepository;
import br.com.qa.ai.model.BusinessRule;

public class KnowledgeMain {

    public static void main(String[] args) {

        BusinessRuleRepository repository =
                new BusinessRuleRepository();

        var rules =
                repository.loadAll();

        System.out.println("\n======================================");
        System.out.println("BASE DE CONHECIMENTO");
        System.out.println("======================================");

        for (BusinessRule rule : rules) {

            System.out.println("\nID: "
                    + rule.id());

            System.out.println("Título: "
                    + rule.titulo());

            System.out.println("Funcionalidade: "
                    + rule.funcionalidade());

            System.out.println("Regra: "
                    + rule.regra());

            System.out.println("Condição: "
                    + rule.condicao());

            System.out.println("Comportamento esperado: "
                    + rule.comportamentoEsperado());

            System.out.println("Exceções: "
                    + rule.excecoes());

            System.out.println("Termos relacionados: "
                    + rule.termosRelacionados());
        }

        System.out.println("\n======================================");

        System.out.println(
                "Total de regras: " + rules.size()
        );
    }
}