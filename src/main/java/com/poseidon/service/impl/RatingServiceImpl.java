package com.poseidon.service.impl;

import com.poseidon.domain.Rating;
import com.poseidon.dto.rating.RatingRequest;
import com.poseidon.dto.rating.RatingResponse;
import com.poseidon.exception.EntityNotFoundException;
import com.poseidon.mapper.RatingMapper;
import com.poseidon.repository.RatingRepository;
import com.poseidon.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository repository;

    @Override
    public RatingResponse create(RatingRequest dto) {
        Rating saved = repository.save(RatingMapper.toEntity(dto));
        return RatingMapper.toResponse(saved);
    }

    @Override
    public Page<RatingResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(RatingMapper::toResponse);
    }

    @Override
    public RatingResponse findById(Integer id) {
        Rating e = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rating not found: " + id));
        return RatingMapper.toResponse(e);
    }

    @Override
    public RatingResponse update(Integer id, RatingRequest dto) {
        Rating e = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rating not found: " + id));
        RatingMapper.updateEntity(e, dto);
        return RatingMapper.toResponse(repository.save(e));
    }

    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Rating not found: " + id);
        }
        repository.deleteById(id);
    }
}
