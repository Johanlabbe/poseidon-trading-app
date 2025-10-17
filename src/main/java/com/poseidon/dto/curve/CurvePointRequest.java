package com.poseidon.dto.curve;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

public record CurvePointRequest(
        @NotNull Integer curveId,
        LocalDateTime asOfDate,
        @PositiveOrZero Double term,
        @PositiveOrZero Double value,
        LocalDateTime creationDate
) {}
