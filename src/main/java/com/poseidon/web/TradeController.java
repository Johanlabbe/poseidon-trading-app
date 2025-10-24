package com.poseidon.web;

import com.poseidon.dto.trade.TradeRequest;
import com.poseidon.dto.trade.TradeResponse;
import com.poseidon.service.TradeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/trades")
@RequiredArgsConstructor
public class TradeController {

    private final TradeService service;

    @PostMapping
    public ResponseEntity<TradeResponse> create(@Valid @RequestBody TradeRequest dto) {
        TradeResponse created = service.create(dto);
        return ResponseEntity.created(URI.create("/api/trades/" + created.tradeId())).body(created);
    }

    @GetMapping
    public Page<TradeResponse> findAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping("/{id}")
    public TradeResponse findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public TradeResponse update(@PathVariable Integer id, @Valid @RequestBody TradeRequest dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
