package com.poseidon.dto.rule;

public record RuleResponse(
        Integer id,
        String name,
        String description,
        String json,
        String template,
        String sqlStr,
        String sqlPart
) {}
