package com.poseidon.dto.bid;

import java.time.LocalDateTime;

public record BidResponse(
        Integer bidListId,
        String account,
        String type,
        Double bidQuantity,
        Double askQuantity,
        Double bid,
        Double ask,
        String benchmark,
        LocalDateTime bidListDate,
        String commentary,
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
