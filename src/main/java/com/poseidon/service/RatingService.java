package com.poseidon.service;

import com.poseidon.dto.rating.RatingRequest;
import com.poseidon.dto.rating.RatingResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RatingService {
    RatingResponse create(RatingRequest dto);
    Page<RatingResponse> findAll(Pageable pageable);
    RatingResponse findById(Integer id);
    RatingResponse update(Integer id, RatingRequest dto);
    void delete(Integer id);
}
