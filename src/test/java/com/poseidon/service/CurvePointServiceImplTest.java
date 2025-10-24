package com.poseidon.service;

import com.poseidon.domain.CurvePoint;
import com.poseidon.dto.curve.CurvePointRequest;
import com.poseidon.exception.EntityNotFoundException;
import com.poseidon.repository.CurvePointRepository;
import com.poseidon.service.impl.CurvePointServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CurvePointServiceImplTest {

    private CurvePointRepository repo;
    private CurvePointService service;

    @BeforeEach
    void setup() {
        repo = Mockito.mock(CurvePointRepository.class);
        service = new CurvePointServiceImpl(repo);
    }

    @Test
    void create_ok() {
        CurvePointRequest req = new CurvePointRequest(10, LocalDateTime.now(), 1.5, 99.9, null);
        when(repo.save(any())).thenAnswer(inv -> {
            CurvePoint e = inv.getArgument(0);
            e.setId(1);
            return e;
        });

        var res = service.create(req);

        assertEquals(1, res.id());
        assertEquals(10, res.curveId());
        assertEquals(1.5, res.term());
        assertEquals(99.9, res.value());
        verify(repo).save(any(CurvePoint.class));
    }

    @Test
    void findAll_ok() {
        Pageable p = PageRequest.of(0, 10, Sort.by("id").ascending());
        when(repo.findAll(p)).thenReturn(new PageImpl<>(
                List.of(CurvePoint.builder().id(1).curveId(10).term(1.0).value(2.0).build())));

        var page = service.findAll(p);

        assertEquals(1, page.getTotalElements());
        assertEquals(1, page.getContent().get(0).id());
    }

    @Test
    void findById_notFound() {
        when(repo.findById(99)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.findById(99));
    }

    @Test
    void update_ok() {
        CurvePoint existing = CurvePoint.builder().id(1).curveId(10).term(1.0).value(2.0).build();
        when(repo.findById(1)).thenReturn(Optional.of(existing));
        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        CurvePointRequest req = new CurvePointRequest(11, null, 2.5, 123.45, null);
        var res = service.update(1, req);

        assertEquals(1, res.id());
        assertEquals(11, res.curveId());
        assertEquals(2.5, res.term());
        assertEquals(123.45, res.value());
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
