package com.poseidon.service;

import com.poseidon.dto.rule.RuleRequest;
import com.poseidon.dto.rule.RuleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RuleService {
    RuleResponse create(RuleRequest dto);
    Page<RuleResponse> findAll(Pageable pageable);
    RuleResponse findById(Integer id);
    RuleResponse update(Integer id, RuleRequest dto);
    void delete(Integer id);
}
