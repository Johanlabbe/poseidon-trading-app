package com.poseidon.dto.curve;

import java.time.LocalDateTime;

public record CurvePointResponse(
        Integer id,
        Integer curveId,
        LocalDateTime asOfDate,
        Double term,
        Double value,
        LocalDateTime creationDate
) {}
