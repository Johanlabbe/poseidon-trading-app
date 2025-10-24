package com.poseidon.dto.trade;

import java.time.LocalDateTime;

public record TradeResponse(
        Integer tradeId,
        String account,
        String type,
        Double buyQuantity,
        Double sellQuantity,
        Double buyPrice,
        Double sellPrice,
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
