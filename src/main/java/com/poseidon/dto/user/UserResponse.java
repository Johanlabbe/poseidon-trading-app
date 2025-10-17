package com.poseidon.dto.user;

public record UserResponse(
        Integer id,
        String username,
        String fullname,
        String role
) {}
