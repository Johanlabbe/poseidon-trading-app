package com.poseidon.service.impl;

import com.poseidon.domain.Bid;
import com.poseidon.dto.bid.BidRequest;
import com.poseidon.dto.bid.BidResponse;
import com.poseidon.exception.EntityNotFoundException;
import com.poseidon.mapper.BidMapper;
import com.poseidon.repository.BidRepository;
import com.poseidon.service.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BidServiceImpl implements BidService {

    private final BidRepository repository;

    @Override
    public BidResponse create(BidRequest dto) {
        Bid saved = repository.save(BidMapper.toEntity(dto));
        return BidMapper.toResponse(saved);
    }

    @Override
    public Page<BidResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(BidMapper::toResponse);
    }

    @Override
    public BidResponse findById(Integer id) {
        Bid e = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Bid not found: " + id));
        return BidMapper.toResponse(e);
    }

    @Override
    public BidResponse update(Integer id, BidRequest dto) {
        Bid e = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Bid not found: " + id));
        BidMapper.updateEntity(e, dto);
        return BidMapper.toResponse(repository.save(e));
    }

    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id))
            throw new EntityNotFoundException("Bid not found: " + id);
        repository.deleteById(id);
    }

    @Override
    public Page<BidResponse> searchByAccount(String q, Pageable pageable) {
        return repository.findByAccountContainingIgnoreCase(q, pageable)
                .map(BidMapper::toResponse);
    }

    @Override
    public Page<BidResponse> searchByTrader(String q, Pageable pageable) {
        return repository.findByTraderContainingIgnoreCase(q, pageable)
                .map(BidMapper::toResponse);
    }

    @Override
    public Page<BidResponse> searchByBidRange(Double min, Double max, Pageable pageable) {
        double lo = (min == null ? 0d : min);
        double hi = (max == null ? Double.MAX_VALUE : max);
        return repository.findByBidBetween(lo, hi, pageable)
                .map(BidMapper::toResponse);
    }
}
