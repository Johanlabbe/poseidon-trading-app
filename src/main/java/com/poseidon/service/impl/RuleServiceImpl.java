package com.poseidon.service.impl;

import com.poseidon.domain.Rule;
import com.poseidon.dto.rule.RuleRequest;
import com.poseidon.dto.rule.RuleResponse;
import com.poseidon.exception.EntityNotFoundException;
import com.poseidon.mapper.RuleMapper;
import com.poseidon.repository.RuleRepository;
import com.poseidon.service.RuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RuleServiceImpl implements RuleService {

    private final RuleRepository repository;

    @Override
    public RuleResponse create(RuleRequest dto) {
        Rule saved = repository.save(RuleMapper.toEntity(dto));
        return RuleMapper.toResponse(saved);
    }

    @Override
    public Page<RuleResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(RuleMapper::toResponse);
    }

    @Override
    public RuleResponse findById(Integer id) {
        Rule e = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rule not found: " + id));
        return RuleMapper.toResponse(e);
    }

    @Override
    public RuleResponse update(Integer id, RuleRequest dto) {
        Rule e = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rule not found: " + id));
        RuleMapper.updateEntity(e, dto);
        return RuleMapper.toResponse(repository.save(e));
    }

    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Rule not found: " + id);
        }
        repository.deleteById(id);
    }
}
