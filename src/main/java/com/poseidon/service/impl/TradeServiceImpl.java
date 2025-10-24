package com.poseidon.service.impl;

import com.poseidon.domain.Trade;
import com.poseidon.dto.trade.TradeRequest;
import com.poseidon.dto.trade.TradeResponse;
import com.poseidon.exception.EntityNotFoundException;
import com.poseidon.mapper.TradeMapper;
import com.poseidon.repository.TradeRepository;
import com.poseidon.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService {

    private final TradeRepository repository;

    @Override
    public TradeResponse create(TradeRequest dto) {
        Trade saved = repository.save(TradeMapper.toEntity(dto));
        return TradeMapper.toResponse(saved);
    }

    @Override
    public Page<TradeResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(TradeMapper::toResponse);
    }

    @Override
    public TradeResponse findById(Integer id) {
        Trade e = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Trade not found: " + id));
        return TradeMapper.toResponse(e);
    }

    @Override
    public TradeResponse update(Integer id, TradeRequest dto) {
        Trade e = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Trade not found: " + id));
        TradeMapper.updateEntity(e, dto);
        return TradeMapper.toResponse(repository.save(e));
    }

    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Trade not found: " + id);
        }
        repository.deleteById(id);
    }
}
