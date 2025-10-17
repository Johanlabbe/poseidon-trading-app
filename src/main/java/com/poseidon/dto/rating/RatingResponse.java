package com.poseidon.dto.rating;

public record RatingResponse(
        Integer id,
        String moodysRating,
        String sandPRating,
        String fitchRating,
        Integer orderNumber
) {}
