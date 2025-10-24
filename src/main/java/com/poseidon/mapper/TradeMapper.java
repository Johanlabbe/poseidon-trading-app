package com.poseidon.mapper;

import com.poseidon.domain.Trade;
import com.poseidon.dto.trade.TradeRequest;
import com.poseidon.dto.trade.TradeResponse;

public final class TradeMapper {
    private TradeMapper() {}

    public static Trade toEntity(TradeRequest dto) {
        if (dto == null) return null;
        return Trade.builder()
                .account(dto.account())
                .type(dto.type())
                .buyQuantity(dto.buyQuantity())
                .sellQuantity(dto.sellQuantity())
                .buyPrice(dto.buyPrice())
                .sellPrice(dto.sellPrice())
                .benchmark(dto.benchmark())
                .tradeDate(dto.tradeDate())
                .security(dto.security())
                .status(dto.status())
                .trader(dto.trader())
                .book(dto.book())
                .creationName(dto.creationName())
                .creationDate(dto.creationDate())
                .revisionName(dto.revisionName())
                .revisionDate(dto.revisionDate())
                .dealName(dto.dealName())
                .dealType(dto.dealType())
                .sourceListId(dto.sourceListId())
                .side(dto.side())
                .build();
    }

    public static void updateEntity(Trade e, TradeRequest dto) {
        e.setAccount(dto.account());
        e.setType(dto.type());
        e.setBuyQuantity(dto.buyQuantity());
        e.setSellQuantity(dto.sellQuantity());
        e.setBuyPrice(dto.buyPrice());
        e.setSellPrice(dto.sellPrice());
        e.setBenchmark(dto.benchmark());
        e.setTradeDate(dto.tradeDate());
        e.setSecurity(dto.security());
        e.setStatus(dto.status());
        e.setTrader(dto.trader());
        e.setBook(dto.book());
        e.setCreationName(dto.creationName());
        e.setCreationDate(dto.creationDate());
        e.setRevisionName(dto.revisionName());
        e.setRevisionDate(dto.revisionDate());
        e.setDealName(dto.dealName());
        e.setDealType(dto.dealType());
        e.setSourceListId(dto.sourceListId());
        e.setSide(dto.side());
    }

    public static TradeResponse toResponse(Trade e) {
        if (e == null) return null;
        return new TradeResponse(
                e.getTradeId(), e.getAccount(), e.getType(),
                e.getBuyQuantity(), e.getSellQuantity(),
                e.getBuyPrice(), e.getSellPrice(),
                e.getBenchmark(), e.getTradeDate(), e.getSecurity(),
                e.getStatus(), e.getTrader(), e.getBook(),
                e.getCreationName(), e.getCreationDate(),
                e.getRevisionName(), e.getRevisionDate(),
                e.getDealName(), e.getDealType(),
                e.getSourceListId(), e.getSide()
        );
    }
}
