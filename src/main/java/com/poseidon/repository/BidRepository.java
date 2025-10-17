package com.poseidon.repository;

import com.poseidon.domain.Bid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<Bid, Integer> {

    Page<Bid> findByAccountContainingIgnoreCase(String account, Pageable pageable);

    Page<Bid> findByTraderContainingIgnoreCase(String trader, Pageable pageable);

    Page<Bid> findByBidBetween(Double min, Double max, Pageable pageable);
}