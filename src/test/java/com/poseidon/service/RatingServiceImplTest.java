package com.poseidon.service;

import com.poseidon.domain.Rating;
import com.poseidon.dto.rating.RatingRequest;
import com.poseidon.exception.EntityNotFoundException;
import com.poseidon.repository.RatingRepository;
import com.poseidon.service.impl.RatingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RatingServiceImplTest {

    private RatingRepository repo;
    private RatingService service;

    @BeforeEach
    void setup() {
        repo = Mockito.mock(RatingRepository.class);
        service = new RatingServiceImpl(repo);
    }

    @Test
    void create_ok() {
        RatingRequest req = new RatingRequest("Aaa", "AAA", "AAA", 1);
        when(repo.save(any())).thenAnswer(inv -> {
            Rating r = inv.getArgument(0);
            r.setId(1);
            return r;
        });

        var res = service.create(req);

        assertEquals(1, res.id());
        assertEquals("Aaa", res.moodysRating());
        assertEquals(1, res.orderNumber());
        verify(repo).save(any(Rating.class));
    }

    @Test
    void findAll_ok() {
        Pageable p = PageRequest.of(0, 10, Sort.by("id").ascending());
        when(repo.findAll(p)).thenReturn(new PageImpl<>(
                List.of(Rating.builder().id(1).moodysRating("Aaa").sandPRating("AAA").fitchRating("AAA").orderNumber(1).build())
        ));

        var page = service.findAll(p);

        assertEquals(1, page.getTotalElements());
        assertEquals("Aaa", page.getContent().get(0).moodysRating());
    }

    @Test
    void findById_notFound() {
        when(repo.findById(99)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.findById(99));
    }

    @Test
    void update_ok() {
        Rating existing = Rating.builder().id(1).moodysRating("A1").sandPRating("A+").fitchRating("A+").orderNumber(2).build();
        when(repo.findById(1)).thenReturn(Optional.of(existing));
        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        RatingRequest req = new RatingRequest("Aa2", "AA", "AA", 3);
        var res = service.update(1, req);

        assertEquals(1, res.id());
        assertEquals("Aa2", res.moodysRating());
        assertEquals("AA", res.sandPRating());
        assertEquals(3, res.orderNumber());
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
