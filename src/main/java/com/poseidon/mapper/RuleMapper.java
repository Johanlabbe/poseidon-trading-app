package com.poseidon.mapper;

import com.poseidon.domain.Rule;
import com.poseidon.dto.rule.RuleRequest;
import com.poseidon.dto.rule.RuleResponse;

public final class RuleMapper {
    private RuleMapper() {}

    public static Rule toEntity(RuleRequest dto) {
        if (dto == null) return null;
        return Rule.builder()
                .name(dto.name())
                .description(dto.description())
                .json(dto.json())
                .template(dto.template())
                .sqlStr(dto.sqlStr())
                .sqlPart(dto.sqlPart())
                .build();
    }

    public static void updateEntity(Rule e, RuleRequest dto) {
        e.setName(dto.name());
        e.setDescription(dto.description());
        e.setJson(dto.json());
        e.setTemplate(dto.template());
        e.setSqlStr(dto.sqlStr());
        e.setSqlPart(dto.sqlPart());
    }

    public static RuleResponse toResponse(Rule e) {
        if (e == null) return null;
        return new RuleResponse(
                e.getId(),
                e.getName(),
                e.getDescription(),
                e.getJson(),
                e.getTemplate(),
                e.getSqlStr(),
                e.getSqlPart()
        );
    }
}
