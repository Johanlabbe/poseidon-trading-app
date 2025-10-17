package com.poseidon.dto.user;

import jakarta.validation.constraints.*;

public record UserSignupRequest(
        @NotBlank String username,
        @NotBlank @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s]).{8,}$",
                message = "Password must be at least 8 chars, contain an uppercase, a digit and a symbol")
        String password,
        @NotBlank String fullname,
        @NotBlank String role
) {}
