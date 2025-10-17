package com.poseidon.dto.rating;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RatingRequest(
        @NotBlank String moodysRating,
        @NotBlank String sandPRating,
        @NotBlank String fitchRating,
        @NotNull @Positive Integer orderNumber
) {}
