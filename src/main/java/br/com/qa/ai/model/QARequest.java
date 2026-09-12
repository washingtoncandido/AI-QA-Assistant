package br.com.qa.ai.model;

import java.util.List;

public record QARequest(
        Context context,
        UserStory userStory,
        List<BusinessRule> businessRules
) {
}