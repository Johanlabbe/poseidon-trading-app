package com.poseidon.web;

import com.poseidon.dto.user.UserCreateRequest;
import com.poseidon.dto.user.UserResponse;
import com.poseidon.dto.user.UserUpdateRequest;
import com.poseidon.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserCreateRequest dto) {
        UserResponse created = service.create(dto);
        return ResponseEntity.created(URI.create("/api/users/" + created.id())).body(created);
    }

    @GetMapping
    public Page<UserResponse> findAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Integer id, @Valid @RequestBody UserUpdateRequest dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
