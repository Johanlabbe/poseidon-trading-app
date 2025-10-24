package com.poseidon.dto.trade;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

public record TradeRequest(
        @NotBlank String account,
        @NotBlank String type,
        @PositiveOrZero Double buyQuantity,
        @PositiveOrZero Double sellQuantity,
        @PositiveOrZero Double buyPrice,
        @PositiveOrZero Double sellPrice,
        String benchmark,
        LocalDateTime tradeDate,
        String security,
        String status,
        String trader,
        String book,
        String creationName,
        LocalDateTime creationDate,
        String revisionName,
        LocalDateTime revisionDate,
        String dealName,
        String dealType,
        String sourceListId,
        String side
) {}
