package br.com.qa.ai.model;

import java.util.List;

public record UserStory(
        String titulo,
        String como,
        String quero,
        String para,
        List<AcceptanceCriterion> acceptanceCriteria
) {
}