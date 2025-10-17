package com.poseidon.web;

import com.poseidon.dto.curve.CurvePointRequest;
import com.poseidon.dto.curve.CurvePointResponse;
import com.poseidon.service.CurvePointService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/curve-points")
@RequiredArgsConstructor
public class CurvePointController {

    private final CurvePointService service;

    @PostMapping
    public ResponseEntity<CurvePointResponse> create(@Valid @RequestBody CurvePointRequest dto) {
        CurvePointResponse created = service.create(dto);
        return ResponseEntity.created(URI.create("/api/curve-points/" + created.id())).body(created);
    }

    @GetMapping
    public Page<CurvePointResponse> findAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping("/{id}")
    public CurvePointResponse findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public CurvePointResponse update(@PathVariable Integer id, @Valid @RequestBody CurvePointRequest dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
