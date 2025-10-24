package com.poseidon.service;

import com.poseidon.dto.trade.TradeRequest;
import com.poseidon.dto.trade.TradeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TradeService {
    TradeResponse create(TradeRequest dto);
    Page<TradeResponse> findAll(Pageable pageable);
    TradeResponse findById(Integer id);
    TradeResponse update(Integer id, TradeRequest dto);
    void delete(Integer id);
}
