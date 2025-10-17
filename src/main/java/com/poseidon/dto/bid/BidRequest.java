package com.poseidon.dto.bid;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public record BidRequest(
        @NotBlank String account,
        @NotBlank String type,
        @PositiveOrZero(message = "bidQuantity must be >= 0") Double bidQuantity,
        @PositiveOrZero(message = "askQuantity must be >= 0") Double askQuantity,
        @PositiveOrZero(message = "bid must be >= 0") Double bid,
        @PositiveOrZero(message = "ask must be >= 0") Double ask,
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
