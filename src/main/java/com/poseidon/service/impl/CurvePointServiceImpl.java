package com.poseidon.service.impl;

import com.poseidon.domain.CurvePoint;
import com.poseidon.dto.curve.CurvePointRequest;
import com.poseidon.dto.curve.CurvePointResponse;
import com.poseidon.exception.EntityNotFoundException;
import com.poseidon.mapper.CurvePointMapper;
import com.poseidon.repository.CurvePointRepository;
import com.poseidon.service.CurvePointService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurvePointServiceImpl implements CurvePointService {

    private final CurvePointRepository repository;

    @Override
    public CurvePointResponse create(CurvePointRequest dto) {
        CurvePoint saved = repository.save(CurvePointMapper.toEntity(dto));
        return CurvePointMapper.toResponse(saved);
    }

    @Override
    public Page<CurvePointResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(CurvePointMapper::toResponse);
    }

    @Override
    public CurvePointResponse findById(Integer id) {
        CurvePoint e = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("CurvePoint not found: " + id));
        return CurvePointMapper.toResponse(e);
    }

    @Override
    public CurvePointResponse update(Integer id, CurvePointRequest dto) {
        CurvePoint e = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("CurvePoint not found: " + id));
        CurvePointMapper.updateEntity(e, dto);
        return CurvePointMapper.toResponse(repository.save(e));
    }

    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("CurvePoint not found: " + id);
        }
        repository.deleteById(id);
    }
}
