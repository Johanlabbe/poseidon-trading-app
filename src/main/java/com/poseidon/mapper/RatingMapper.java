package com.poseidon.mapper;

import com.poseidon.domain.Rating;
import com.poseidon.dto.rating.RatingRequest;
import com.poseidon.dto.rating.RatingResponse;

public final class RatingMapper {
    private RatingMapper() {}

    public static Rating toEntity(RatingRequest dto) {
        if (dto == null) return null;
        return Rating.builder()
                .moodysRating(dto.moodysRating())
                .sandPRating(dto.sandPRating())
                .fitchRating(dto.fitchRating())
                .orderNumber(dto.orderNumber())
                .build();
    }

    public static void updateEntity(Rating e, RatingRequest dto) {
        e.setMoodysRating(dto.moodysRating());
        e.setSandPRating(dto.sandPRating());
        e.setFitchRating(dto.fitchRating());
        e.setOrderNumber(dto.orderNumber());
    }

    public static RatingResponse toResponse(Rating e) {
        if (e == null) return null;
        return new RatingResponse(
                e.getId(),
                e.getMoodysRating(),
                e.getSandPRating(),
                e.getFitchRating(),
                e.getOrderNumber()
        );
    }
}
