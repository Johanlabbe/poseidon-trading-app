package com.poseidon.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserUpdateRequest(
        @NotBlank String username,
        String password,
        @NotBlank String fullname,
        @Pattern(regexp = "^ROLE_(USER|ADMIN)$", message = "Rôle attendu: ROLE_USER ou ROLE_ADMIN")
        String role
) {}
