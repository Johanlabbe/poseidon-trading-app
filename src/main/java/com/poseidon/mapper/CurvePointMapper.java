package com.poseidon.mapper;

import com.poseidon.domain.CurvePoint;
import com.poseidon.dto.curve.CurvePointRequest;
import com.poseidon.dto.curve.CurvePointResponse;

public final class CurvePointMapper {
    private CurvePointMapper() {}

    public static CurvePoint toEntity(CurvePointRequest dto) {
        if (dto == null) return null;
        return CurvePoint.builder()
                .curveId(dto.curveId())
                .asOfDate(dto.asOfDate())
                .term(dto.term())
                .value(dto.value())
                .creationDate(dto.creationDate())
                .build();
    }

    public static void updateEntity(CurvePoint e, CurvePointRequest dto) {
        e.setCurveId(dto.curveId());
        e.setAsOfDate(dto.asOfDate());
        e.setTerm(dto.term());
        e.setValue(dto.value());
        e.setCreationDate(dto.creationDate());
    }

    public static CurvePointResponse toResponse(CurvePoint e) {
        if (e == null) return null;
        return new CurvePointResponse(
                e.getId(),
                e.getCurveId(),
                e.getAsOfDate(),
                e.getTerm(),
                e.getValue(),
                e.getCreationDate()
        );
    }
}
