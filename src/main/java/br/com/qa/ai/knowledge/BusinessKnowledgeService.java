package br.com.qa.ai.knowledge;

import br.com.qa.ai.model.BusinessRule;

import java.util.List;

public class BusinessKnowledgeService {

    private final BusinessRuleRepository repository;

    public BusinessKnowledgeService() {
        this.repository = new BusinessRuleRepository();
    }

    public List<BusinessRule> getBusinessRules(String fileName) {
        return repository.load(fileName);
    }

    public List<BusinessRule> getAllBusinessRules() {
        return repository.loadAll();
    }

    public String getBusinessRulesAsText(String fileName) {

        List<BusinessRule> rules =
                getBusinessRules(fileName);

        if (rules.isEmpty()) {
            return "Nenhuma regra de negócio encontrada.";
        }

        StringBuilder result = new StringBuilder();

        for (BusinessRule rule : rules) {

            result.append(rule.id())
                    .append(" - ")
                    .append(rule.titulo())
                    .append("\n");

            result.append("Funcionalidade: ")
                    .append(rule.funcionalidade())
                    .append("\n");

            result.append("Regra: ")
                    .append(rule.regra())
                    .append("\n");

            result.append("Condição: ")
                    .append(rule.condicao())
                    .append("\n");

            result.append("Comportamento esperado: ")
                    .append(rule.comportamentoEsperado())
                    .append("\n");

            result.append("Exceções: ")
                    .append(rule.excecoes())
                    .append("\n");

            result.append("Termos relacionados: ")
                    .append(String.join(
                            ", ",
                            rule.termosRelacionados()
                    ))
                    .append("\n\n");
        }

        return result.toString().trim();
    }
}