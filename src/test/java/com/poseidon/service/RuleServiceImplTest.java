package com.poseidon.service;

import com.poseidon.domain.Rule;
import com.poseidon.dto.rule.RuleRequest;
import com.poseidon.exception.EntityNotFoundException;
import com.poseidon.repository.RuleRepository;
import com.poseidon.service.impl.RuleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RuleServiceImplTest {

    private RuleRepository repo;
    private RuleService service;

    @BeforeEach
    void setup() {
        repo = Mockito.mock(RuleRepository.class);
        service = new RuleServiceImpl(repo);
    }

    @Test
    void create_ok() {
        RuleRequest req = new RuleRequest("PnL Check", "desc", "{}", "TPL", "SELECT 1", "1=1");
        when(repo.save(any())).thenAnswer(inv -> {
            Rule r = inv.getArgument(0);
            r.setId(1);
            return r;
        });

        var res = service.create(req);

        assertEquals(1, res.id());
        assertEquals("PnL Check", res.name());
        verify(repo).save(any(Rule.class));
    }

    @Test
    void findAll_ok() {
        Pageable p = PageRequest.of(0, 10, Sort.by("id").ascending());
        when(repo.findAll(p)).thenReturn(new PageImpl<>(
                List.of(Rule.builder().id(1).name("R1").description("d").json("{}").template("T").sqlStr("S").sqlPart("P").build())
        ));

        var page = service.findAll(p);

        assertEquals(1, page.getTotalElements());
        assertEquals("R1", page.getContent().get(0).name());
    }

    @Test
    void findById_notFound() {
        when(repo.findById(99)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.findById(99));
    }

    @Test
    void update_ok() {
        Rule existing = Rule.builder().id(1).name("Old").description("d").json("{}").template("T").sqlStr("S").sqlPart("P").build();
        when(repo.findById(1)).thenReturn(Optional.of(existing));
        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        RuleRequest req = new RuleRequest("New", "new d", "{\"k\":1}", "TPL2", "SELECT 2", "2=2");
        var res = service.update(1, req);

        assertEquals(1, res.id());
        assertEquals("New", res.name());
        assertEquals("TPL2", res.template());
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
