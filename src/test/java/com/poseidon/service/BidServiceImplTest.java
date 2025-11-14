package com.poseidon.service;

import com.poseidon.domain.Bid;
import com.poseidon.dto.bid.BidRequest;
import com.poseidon.exception.EntityNotFoundException;
import com.poseidon.repository.BidRepository;
import com.poseidon.service.impl.BidServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BidServiceImplTest {

    private BidRepository repo;
    private BidService service;

    @BeforeEach
    void setup() {
        repo = mock(BidRepository.class);
        service = new BidServiceImpl(repo);
    }

    private static Bid entity(Integer id, String account, String type, Double bid) {
        Bid b = new Bid();
        b.setBidListId(id);
        b.setAccount(account);
        b.setType(type);
        b.setBid(bid);
        b.setTrader("TRADER-1");
        b.setBook("BOOK-A");
        b.setStatus("OPEN");
        b.setSide("BUY");
        return b;
    }

    private static BidRequest req(String account, String type, Double bid) {
        return new BidRequest(
            account, type,
            10.0, 5.0,
            bid, 1.3,
            "BMK", null,
            null, null,
            "OPEN", "TRADER-1", "BOOK-A",
            null, null,
            null, null,
            null, null,
            null,
            "BUY"
        );
    }

    @Test
    void create_ok() {
        when(repo.save(any())).thenAnswer(inv -> {
            Bid b = inv.getArgument(0);
            b.setBidListId(1);
            return b;
        });

        var res = service.create(req("ACC-001", "LIMIT", 1.2));

        assertEquals(1, res.bidListId());
        assertEquals("ACC-001", res.account());
        verify(repo).save(any(Bid.class));
    }

    @Test
    void findAll_ok() {
        Pageable p = PageRequest.of(0, 10);
        when(repo.findAll(p)).thenReturn(new PageImpl<>(List.of(entity(1, "ACC-001", "LIMIT", 1.2))));

        var page = service.findAll(p);

        assertEquals(1, page.getTotalElements());
        assertEquals("ACC-001", page.getContent().get(0).account());
    }

    @Test
    void findById_ok() {
        when(repo.findById(1)).thenReturn(Optional.of(entity(1, "ACC-001", "LIMIT", 1.2)));

        var res = service.findById(1);

        assertEquals(1, res.bidListId());
        assertEquals("ACC-001", res.account());
    }

    @Test
    void findById_notFound() {
        when(repo.findById(99)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.findById(99));
    }

    @Test
    void update_ok() {
        Bid existing = entity(1, "ACC-001", "LIMIT", 1.2);
        when(repo.findById(1)).thenReturn(Optional.of(existing));
        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        var res = service.update(1, req("ACC-UPDATED", "MARKET", 2.5));

        assertEquals(1, res.bidListId());
        assertEquals("ACC-UPDATED", res.account());
        assertEquals("MARKET", res.type());
        verify(repo).save(existing);
    }

    @Test
    void update_notFound() {
        when(repo.findById(7)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.update(7, req("A","L",1.0)));
        verify(repo, never()).save(any());
    }

    @Test
    void delete_ok() {
        when(repo.existsById(1)).thenReturn(true);
        service.delete(1);
        verify(repo).deleteById(1);
    }

    @Test
    void delete_notFound() {
        when(repo.existsById(42)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.delete(42));
        verify(repo, never()).deleteById(anyInt());
    }

    @Test
    void searchByAccount_ok() {
        Pageable p = PageRequest.of(0, 5);
        when(repo.findByAccountContainingIgnoreCase(anyString(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(entity(1, "ACC-001", "LIMIT", 1.2))));

        var page = service.searchByAccount("acc", p);

        assertEquals(1, page.getTotalElements());
        verify(repo).findByAccountContainingIgnoreCase("acc", p);
    }

    @Test
    void searchByTrader_ok() {
        Pageable p = PageRequest.of(0, 5);
        when(repo.findByTraderContainingIgnoreCase(anyString(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(entity(1, "ACC-001", "LIMIT", 1.2))));

        var page = service.searchByTrader("trader", p);

        assertEquals(1, page.getTotalElements());
        verify(repo).findByTraderContainingIgnoreCase("trader", p);
    }

    @Test
    void searchByBidRange_bothNull_min0_maxMax() {
        Pageable p = PageRequest.of(0, 5);
        when(repo.findByBidBetween(anyDouble(), anyDouble(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(entity(1, "ACC-001", "LIMIT", 1.2))));

        service.searchByBidRange(null, null, p);

        ArgumentCaptor<Double> lo = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Double> hi = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Pageable> capP = ArgumentCaptor.forClass(Pageable.class);
        verify(repo).findByBidBetween(lo.capture(), hi.capture(), capP.capture());
        assertEquals(0d, lo.getValue());
        assertEquals(Double.MAX_VALUE, hi.getValue());
        assertEquals(p, capP.getValue());
    }

    @Test
    void searchByBidRange_minOnly() {
        Pageable p = PageRequest.of(0, 5);
        when(repo.findByBidBetween(anyDouble(), anyDouble(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(entity(1, "ACC-001", "LIMIT", 1.2))));

        service.searchByBidRange(5.0, null, p);

        ArgumentCaptor<Double> lo = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Double> hi = ArgumentCaptor.forClass(Double.class);
        verify(repo).findByBidBetween(lo.capture(), hi.capture(), any(Pageable.class));
        assertEquals(5.0, lo.getValue());
        assertEquals(Double.MAX_VALUE, hi.getValue());
    }

    @Test
    void searchByBidRange_maxOnly() {
        Pageable p = PageRequest.of(0, 5);
        when(repo.findByBidBetween(anyDouble(), anyDouble(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(entity(1, "ACC-001", "LIMIT", 1.2))));

        service.searchByBidRange(null, 10.0, p);

        ArgumentCaptor<Double> lo = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Double> hi = ArgumentCaptor.forClass(Double.class);
        verify(repo).findByBidBetween(lo.capture(), hi.capture(), any(Pageable.class));
        assertEquals(0d, lo.getValue());
        assertEquals(10.0, hi.getValue());
    }
}
