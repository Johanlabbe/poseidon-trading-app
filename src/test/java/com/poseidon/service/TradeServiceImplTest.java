package com.poseidon.service;

import com.poseidon.domain.Trade;
import com.poseidon.dto.trade.TradeRequest;
import com.poseidon.exception.EntityNotFoundException;
import com.poseidon.repository.TradeRepository;
import com.poseidon.service.impl.TradeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class TradeServiceImplTest {

    private TradeRepository repo;
    private TradeService service;

    @BeforeEach
    void setup() {
        repo = Mockito.mock(TradeRepository.class);
        service = new TradeServiceImpl(repo);
    }

    @Test
    void create_ok() {
        TradeRequest req = new TradeRequest(
                "ACC-TR-001", "MARKET",
                100.0, 0.0, 10.5, 0.0,
                "BMK", LocalDateTime.now(),
                "SEC", "OPEN", "TRADER-1", "BOOK-A",
                "creator", LocalDateTime.now(),
                null, null, null, null, null, "BUY"
        );

        when(repo.save(any())).thenAnswer(inv -> {
            Trade t = inv.getArgument(0);
            t.setTradeId(1);
            return t;
        });

        var res = service.create(req);

        assertEquals(1, res.tradeId());
        assertEquals("ACC-TR-001", res.account());
        assertEquals(100.0, res.buyQuantity());
        verify(repo).save(any(Trade.class));
    }

    @Test
    void findAll_ok() {
        Pageable p = PageRequest.of(0, 10, Sort.by("tradeId").ascending());
        when(repo.findAll(p)).thenReturn(new PageImpl<>(
                List.of(Trade.builder().tradeId(1).account("A").type("MARKET").buyQuantity(50.0).build())
        ));

        var page = service.findAll(p);

        assertEquals(1, page.getTotalElements());
        assertEquals("A", page.getContent().get(0).account());
    }

    @Test
    void findById_notFound() {
        when(repo.findById(99)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.findById(99));
    }

    @Test
    void update_ok() {
        Trade existing = Trade.builder()
                .tradeId(1).account("A").type("MARKET").buyQuantity(100.0).sellQuantity(0.0).build();

        when(repo.findById(1)).thenReturn(Optional.of(existing));
        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        TradeRequest req = new TradeRequest(
            "A", "LIMIT",
            200.0, 0.0, 11.0, 0.0,
            "BMK2", null,
            "SEC2", "CLOSED", "TRADER-2", "BOOK-B",
            null, null, null, null, 
            null, null,        
            null,                
            "SELL"    
        );

        var res = service.update(1, req);

        assertEquals(1, res.tradeId());
        assertEquals("LIMIT", res.type());
        assertEquals(200.0, res.buyQuantity());
        assertEquals("CLOSED", res.status());
        verify(repo).save(existing);
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
}
