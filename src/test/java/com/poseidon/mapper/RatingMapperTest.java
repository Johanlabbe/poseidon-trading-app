package com.poseidon.mapper;

import com.poseidon.domain.Rating;
import com.poseidon.dto.rating.RatingRequest;
import com.poseidon.dto.rating.RatingResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RatingMapperTest {

    @Test
    void toEntity() {
        RatingRequest request = new RatingRequest("M", "S", "F", 10);

        Rating entity = RatingMapper.toEntity(request);

        assertNotNull(entity);
        assertEquals("M", entity.getMoodysRating());
        assertEquals("S", entity.getSandPRating());
        assertEquals(10, entity.getOrderNumber());
    }

    @Test
    void updateEntity() {
        Rating entity = new Rating();
        RatingRequest request = new RatingRequest("M2", "S2", "F2", 20);

        RatingMapper.updateEntity(entity, request);

        assertEquals("M2", entity.getMoodysRating());
        assertEquals(20, entity.getOrderNumber());
    }

    @Test
    void toResponse() {
        Rating entity = Rating.builder().id(1).moodysRating("M").orderNumber(5).build();

        RatingResponse response = RatingMapper.toResponse(entity);

        assertEquals(1, response.id());
        assertEquals("M", response.moodysRating());
        assertEquals(5, response.orderNumber());
    }
}