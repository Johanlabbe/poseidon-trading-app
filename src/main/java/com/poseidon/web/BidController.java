package com.poseidon.web;

import com.poseidon.dto.bid.BidRequest;
import com.poseidon.dto.bid.BidResponse;
import com.poseidon.service.BidService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/bids")
@RequiredArgsConstructor
public class BidController {

    private final BidService service;

    @PostMapping
    public ResponseEntity<BidResponse> create(@Valid @RequestBody BidRequest dto) {
        BidResponse created = service.create(dto);
        return ResponseEntity.created(URI.create("/api/bids/" + created.bidListId())).body(created);
    }

    @GetMapping
    public Page<BidResponse> findAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping("/{id}")
    public BidResponse findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public BidResponse update(@PathVariable Integer id, @Valid @RequestBody BidRequest dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/account")
    public Page<BidResponse> searchByAccount(@RequestParam("q") String q, Pageable pageable) {
        return service.searchByAccount(q, pageable);
    }

    @GetMapping("/search/trader")
    public Page<BidResponse> searchByTrader(@RequestParam("q") String q, Pageable pageable) {
        return service.searchByTrader(q, pageable);
    }

    @GetMapping("/search/bid-range")
    public Page<BidResponse> searchByBidRange(
            @RequestParam(value = "min", required = false) Double min,
            @RequestParam(value = "max", required = false) Double max,
            Pageable pageable) {
        return service.searchByBidRange(min, max, pageable);
    }

}
