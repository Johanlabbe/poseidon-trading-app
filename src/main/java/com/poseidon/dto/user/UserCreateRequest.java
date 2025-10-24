package com.poseidon.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserCreateRequest(
        @NotBlank String username,
        @NotBlank
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s]).{8,}$",
                 message = "Mot de passe: 8+ car., 1 maj, 1 chiffre, 1 symbole")
        String password,
        @NotBlank String fullname,
        @Pattern(regexp = "^ROLE_(USER|ADMIN)$", message = "Rôle attendu: ROLE_USER ou ROLE_ADMIN")
        String role
) {}
