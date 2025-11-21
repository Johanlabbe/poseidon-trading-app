package com.poseidon.mapper;

import com.poseidon.domain.Trade;
import com.poseidon.dto.trade.TradeRequest;
import com.poseidon.dto.trade.TradeResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TradeMapperTest {

    @Test
    void toEntity() {
        TradeRequest request = new TradeRequest(
                "Acc", "Type", 10d, 20d, 30d, 40d, "Bench",
                null, "Sec", "Stat", "Trader", "Book", "CN", null, "RN", null, "DN", "DT", "SID", "Side"
        );

        Trade entity = TradeMapper.toEntity(request);

        assertNotNull(entity);
        assertEquals("Acc", entity.getAccount());
        assertEquals("Type", entity.getType());
        assertEquals(10d, entity.getBuyQuantity());
        assertEquals("Trader", entity.getTrader());
    }

    @Test
    void updateEntity() {
        Trade entity = new Trade();
        TradeRequest request = new TradeRequest(
                "NewAcc", "NewType", 1d, 1d, 1d, 1d, null, null, null, null, null, null, null, null, null, null, null, null, null, null
        );

        TradeMapper.updateEntity(entity, request);

        assertEquals("NewAcc", entity.getAccount());
        assertEquals("NewType", entity.getType());
    }

    @Test
    void toResponse() {
        Trade entity = Trade.builder().tradeId(1).account("Acc").build();

        TradeResponse response = TradeMapper.toResponse(entity);

        assertEquals(1, response.tradeId());
        assertEquals("Acc", response.account());
    }
}