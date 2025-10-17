package com.poseidon.dto.rule;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RuleRequest(
        @NotBlank @Size(max = 255) String name,
        @Size(max = 1000) String description,
        String json,
        String template,
        String sqlStr,
        String sqlPart
) {}
