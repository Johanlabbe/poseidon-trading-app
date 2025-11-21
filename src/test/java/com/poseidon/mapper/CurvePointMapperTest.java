package com.poseidon.mapper;

import com.poseidon.domain.CurvePoint;
import com.poseidon.dto.curve.CurvePointRequest;
import com.poseidon.dto.curve.CurvePointResponse;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CurvePointMapperTest {

    @Test
    void toEntity_shouldMapCorrectly() {
        LocalDateTime now = LocalDateTime.now();
        CurvePointRequest request = new CurvePointRequest(10, now, 12.5, 50.0, now);

        CurvePoint entity = CurvePointMapper.toEntity(request);

        assertNotNull(entity);
        assertEquals(10, entity.getCurveId());
        assertEquals(12.5, entity.getTerm());
        assertEquals(50.0, entity.getValue());
    }

    @Test
    void updateEntity_shouldUpdateValues() {
        CurvePoint entity = new CurvePoint();
        entity.setCurveId(1);
        
        CurvePointRequest request = new CurvePointRequest(99, null, 10.0, 20.0, null);

        CurvePointMapper.updateEntity(entity, request);

        assertEquals(99, entity.getCurveId());
        assertEquals(10.0, entity.getTerm());
    }

    @Test
    void toResponse_shouldMapToDto() {
        CurvePoint entity = CurvePoint.builder()
                .id(5)
                .curveId(10)
                .term(12d)
                .value(15d)
                .build();

        CurvePointResponse response = CurvePointMapper.toResponse(entity);

        assertEquals(5, response.id());
        assertEquals(10, response.curveId());
        assertEquals(12d, response.term());
    }
}