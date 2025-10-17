package com.poseidon.service;

import com.poseidon.dto.bid.BidRequest;
import com.poseidon.dto.bid.BidResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BidService {
    BidResponse create(BidRequest dto);

    Page<BidResponse> findAll(Pageable pageable);

    BidResponse findById(Integer id);

    BidResponse update(Integer id, BidRequest dto);

    void delete(Integer id);

    Page<BidResponse> searchByAccount(String q, Pageable pageable);

    Page<BidResponse> searchByTrader(String q, Pageable pageable);

    Page<BidResponse> searchByBidRange(Double min, Double max, Pageable pageable);
}
