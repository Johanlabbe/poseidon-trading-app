package com.poseidon.mapper;

import com.poseidon.domain.Bid;
import com.poseidon.dto.bid.BidRequest;
import com.poseidon.dto.bid.BidResponse;

public class BidMapper {
    public static Bid toEntity(BidRequest dto) {
        if (dto == null) return null;
        return Bid.builder()
                .account(dto.account())
                .type(dto.type())
                .bidQuantity(dto.bidQuantity())
                .askQuantity(dto.askQuantity())
                .bid(dto.bid())
                .ask(dto.ask())
                .benchmark(dto.benchmark())
                .bidListDate(dto.bidListDate())
                .commentary(dto.commentary())
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

    public static void updateEntity(Bid entity, BidRequest dto) {
        entity.setAccount(dto.account());
        entity.setType(dto.type());
        entity.setBidQuantity(dto.bidQuantity());
        entity.setAskQuantity(dto.askQuantity());
        entity.setBid(dto.bid());
        entity.setAsk(dto.ask());
        entity.setBenchmark(dto.benchmark());
        entity.setBidListDate(dto.bidListDate());
        entity.setCommentary(dto.commentary());
        entity.setSecurity(dto.security());
        entity.setStatus(dto.status());
        entity.setTrader(dto.trader());
        entity.setBook(dto.book());
        entity.setCreationName(dto.creationName());
        entity.setCreationDate(dto.creationDate());
        entity.setRevisionName(dto.revisionName());
        entity.setRevisionDate(dto.revisionDate());
        entity.setDealName(dto.dealName());
        entity.setDealType(dto.dealType());
        entity.setSourceListId(dto.sourceListId());
        entity.setSide(dto.side());
    }

    public static BidResponse toResponse(Bid e) {
        if (e == null) return null;
        return new BidResponse(
                e.getBidListId(), e.getAccount(), e.getType(), e.getBidQuantity(), e.getAskQuantity(),
                e.getBid(), e.getAsk(), e.getBenchmark(), e.getBidListDate(), e.getCommentary(),
                e.getSecurity(), e.getStatus(), e.getTrader(), e.getBook(), e.getCreationName(),
                e.getCreationDate(), e.getRevisionName(), e.getRevisionDate(), e.getDealName(),
                e.getDealType(), e.getSourceListId(), e.getSide()
        );
    }
}
