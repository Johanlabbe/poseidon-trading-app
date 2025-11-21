package com.poseidon.mapper;

import com.poseidon.domain.Bid;
import com.poseidon.dto.bid.BidRequest;
import com.poseidon.dto.bid.BidResponse;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BidMapperTest {

    @Test
    void toEntity_shouldMapAllFields() {
        LocalDateTime now = LocalDateTime.now();
        BidRequest request = new BidRequest(
                "Account1", "TypeA", 10.0, 20.0, 30.0, 40.0, "Bench",
                now, "Comment", "Sec", "Status", "Trader", "Book",
                "CreateName", now, "RevName", now, "DealName", "DealType",
                "SourceId", "Side"
        );

        Bid bid = BidMapper.toEntity(request);

        assertNotNull(bid);
        assertEquals("Account1", bid.getAccount());
        assertEquals("TypeA", bid.getType());
        assertEquals(10.0, bid.getBidQuantity());
        assertEquals(now, bid.getBidListDate());
        assertEquals("Comment", bid.getCommentary());
        assertEquals("SourceId", bid.getSourceListId());
    }

    @Test
    void updateEntity_shouldUpdateFields() {
        Bid bid = new Bid();
        bid.setAccount("OldAccount");
        
        BidRequest request = new BidRequest(
                "NewAccount", "NewType", 5.0, 5.0, 5.0, 5.0, "Bench",
                null, null, null, null, null, null, null, null, null, null, null, null, null, null
        );

        BidMapper.updateEntity(bid, request);

        assertEquals("NewAccount", bid.getAccount());
        assertEquals("NewType", bid.getType());
        assertEquals(5.0, bid.getBidQuantity());
    }

    @Test
    void toResponse_shouldMapToDto() {
        Bid bid = Bid.builder()
                .bidListId(1)
                .account("Acc")
                .type("Type")
                .bidQuantity(100d)
                .build();

        BidResponse response = BidMapper.toResponse(bid);

        assertNotNull(response);
        assertEquals(1, response.bidListId());
        assertEquals("Acc", response.account());
        assertEquals(100d, response.bidQuantity());
    }
}