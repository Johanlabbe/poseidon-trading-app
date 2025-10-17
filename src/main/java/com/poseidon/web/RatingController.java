package com.poseidon.web;

import com.poseidon.dto.rating.RatingRequest;
import com.poseidon.dto.rating.RatingResponse;
import com.poseidon.service.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService service;

    @PostMapping
    public ResponseEntity<RatingResponse> create(@Valid @RequestBody RatingRequest dto) {
        RatingResponse created = service.create(dto);
        return ResponseEntity.created(URI.create("/api/ratings/" + created.id())).body(created);
    }

    @GetMapping
    public Page<RatingResponse> findAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping("/{id}")
    public RatingResponse findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public RatingResponse update(@PathVariable Integer id, @Valid @RequestBody RatingRequest dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
