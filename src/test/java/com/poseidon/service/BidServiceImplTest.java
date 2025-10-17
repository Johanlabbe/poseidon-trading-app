package com.poseidon.service;

import com.poseidon.domain.Bid;
import com.poseidon.dto.bid.BidRequest;
import com.poseidon.exception.EntityNotFoundException;
import com.poseidon.repository.BidRepository;
import com.poseidon.service.impl.BidServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BidServiceImplTest {

    private BidRepository repo;
    private BidService service;

    @BeforeEach
    void setup() {
        repo = Mockito.mock(BidRepository.class);
        service = new BidServiceImpl(repo);
    }

    @Test
    void create_ok() {
        BidRequest req = new BidRequest("acc","type",1.0,2.0,3.0,4.0,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);
        when(repo.save(any())).thenAnswer(inv -> {
            Bid b = inv.getArgument(0);
            b.setBidListId(1);
            return b;
        });
        var res = service.create(req);
        assertEquals(1, res.bidListId());
        verify(repo).save(any());
    }

    @Test
    void findAll_ok() {
        when(repo.findAll(PageRequest.of(0,10))).thenReturn(new PageImpl<>(List.of(Bid.builder().bidListId(1).account("a").type("t").build())));
        Page<?> page = service.findAll(PageRequest.of(0,10));
        assertEquals(1, page.getTotalElements());
    }

    @Test
    void findById_notFound() {
        when(repo.findById(99)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.findById(99));
    }

    @Test
    void delete_ok() {
        when(repo.existsById(1)).thenReturn(true);
        service.delete(1);
        verify(repo).deleteById(1);
    }
}
