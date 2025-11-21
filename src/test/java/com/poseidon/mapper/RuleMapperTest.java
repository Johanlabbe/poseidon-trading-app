package com.poseidon.mapper;

import com.poseidon.domain.Rule;
import com.poseidon.dto.rule.RuleRequest;
import com.poseidon.dto.rule.RuleResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RuleMapperTest {

    @Test
    void toEntity() {
        RuleRequest request = new RuleRequest("Name", "Desc", "Json", "Templ", "SQL", "Part");

        Rule entity = RuleMapper.toEntity(request);

        assertNotNull(entity);
        assertEquals("Name", entity.getName());
        assertEquals("Desc", entity.getDescription());
        assertEquals("Json", entity.getJson());
    }

    @Test
    void updateEntity() {
        Rule entity = new Rule();
        RuleRequest request = new RuleRequest("NewName", "NewDesc", null, null, null, null);

        RuleMapper.updateEntity(entity, request);

        assertEquals("NewName", entity.getName());
        assertEquals("NewDesc", entity.getDescription());
    }

    @Test
    void toResponse() {
        Rule entity = Rule.builder().id(1).name("Name").build();

        RuleResponse response = RuleMapper.toResponse(entity);

        assertEquals(1, response.id());
        assertEquals("Name", response.name());
    }
}