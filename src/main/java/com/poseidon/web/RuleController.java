package com.poseidon.web;

import com.poseidon.dto.rule.RuleRequest;
import com.poseidon.dto.rule.RuleResponse;
import com.poseidon.service.RuleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/rules")
@RequiredArgsConstructor
public class RuleController {

    private final RuleService service;

    @PostMapping
    public ResponseEntity<RuleResponse> create(@Valid @RequestBody RuleRequest dto) {
        RuleResponse created = service.create(dto);
        return ResponseEntity.created(URI.create("/api/rules/" + created.id())).body(created);
    }

    @GetMapping
    public Page<RuleResponse> findAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping("/{id}")
    public RuleResponse findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public RuleResponse update(@PathVariable Integer id, @Valid @RequestBody RuleRequest dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
