package com.poseidon.service;

import com.poseidon.domain.User;
import com.poseidon.dto.user.UserCreateRequest;
import com.poseidon.dto.user.UserUpdateRequest;
import com.poseidon.exception.EntityNotFoundException;
import com.poseidon.repository.UserRepository;
import com.poseidon.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserRepository repo;
    private PasswordEncoder encoder;
    private UserService service;

    @BeforeEach
    void setup() {
        repo = Mockito.mock(UserRepository.class);
        encoder = Mockito.mock(PasswordEncoder.class);
        service = new UserServiceImpl(repo, encoder);
    }

    @Test
    void create_ok() {
        UserCreateRequest req = new UserCreateRequest("jdoe", "User#2025!", "John Doe", "ROLE_USER");
        when(encoder.encode("User#2025!")).thenReturn("ENCODED_HASH");
        when(repo.save(any())).thenAnswer(inv -> {
            User u = inv.getArgument(0);
            u.setId(1);
            return u;
        });

        var res = service.create(req);

        assertEquals(1, res.id());
        assertEquals("jdoe", res.username());
        assertEquals("John Doe", res.fullname());
        assertEquals("ROLE_USER", res.role());

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(repo).save(captor.capture());
        assertEquals("ENCODED_HASH", captor.getValue().getPassword());
        verify(encoder).encode("User#2025!");
    }

    @Test
    void create_duplicateUsername_throws() {
        UserCreateRequest req = new UserCreateRequest("jdoe", "User#2025!", "John Doe", "ROLE_USER");
        when(encoder.encode("User#2025!")).thenReturn("H");
        when(repo.save(any())).thenThrow(new DataIntegrityViolationException("duplicate username"));

        assertThrows(DataIntegrityViolationException.class, () -> service.create(req));
    }

    @Test
    void findAll_ok() {
        Pageable p = PageRequest.of(0, 10, Sort.by("id").ascending());
        when(repo.findAll(p)).thenReturn(new PageImpl<>(
                List.of(User.builder().id(1).username("jdoe").password("H").fullname("John Doe").role("ROLE_USER").build())
        ));

        var page = service.findAll(p);

        assertEquals(1, page.getTotalElements());
        assertEquals("jdoe", page.getContent().get(0).username());
    }

    @Test
    void findById_notFound() {
        when(repo.findById(99)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.findById(99));
    }

    @Test
    void update_ok_keepPassword() {
        User existing = User.builder().id(1).username("jdoe").password("HASH1").fullname("John Doe").role("ROLE_USER").build();
        when(repo.findById(1)).thenReturn(Optional.of(existing));
        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        UserUpdateRequest req = new UserUpdateRequest("jdoe", null, "Johnathan Doe", "ROLE_USER");
        var res = service.update(1, req);

        assertEquals(1, res.id());
        assertEquals("Johnathan Doe", res.fullname());
        assertEquals("ROLE_USER", res.role());
        assertEquals("HASH1", existing.getPassword());
        verify(encoder, never()).encode(anyString());
    }

    @Test
    void update_ok_changePassword() {
        User existing = User.builder().id(1).username("jdoe").password("HASH1").fullname("John Doe").role("ROLE_USER").build();
        when(repo.findById(1)).thenReturn(Optional.of(existing));
        when(encoder.encode("New#2026!")).thenReturn("HASH2");
        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        UserUpdateRequest req = new UserUpdateRequest("jdoe", "New#2026!", "John Doe", "ROLE_ADMIN");
        var res = service.update(1, req);

        assertEquals(1, res.id());
        assertEquals("ROLE_ADMIN", res.role());
        assertEquals("HASH2", existing.getPassword());
        verify(encoder).encode("New#2026!");
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
