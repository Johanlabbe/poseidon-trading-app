package com.poseidon.service.impl;

import com.poseidon.domain.User;
import com.poseidon.dto.user.UserCreateRequest;
import com.poseidon.dto.user.UserResponse;
import com.poseidon.dto.user.UserUpdateRequest;
import com.poseidon.exception.EntityNotFoundException;
import com.poseidon.mapper.UserMapper;
import com.poseidon.repository.UserRepository;
import com.poseidon.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse create(UserCreateRequest dto) {
        User e = new User();
        UserMapper.applyCreate(e, dto, passwordEncoder.encode(dto.password()));
        try {
            return UserMapper.toResponse(repository.save(e));
        } catch (DataIntegrityViolationException ex) {
            throw ex;
        }
    }

    @Override
    public Page<UserResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(UserMapper::toResponse);
    }

    @Override
    public UserResponse findById(Integer id) {
        User e = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
        return UserMapper.toResponse(e);
    }

    @Override
    public UserResponse update(Integer id, UserUpdateRequest dto) {
        User e = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
        String encoded = (dto.password() != null && !dto.password().isBlank())
                ? passwordEncoder.encode(dto.password())
                : null;
        UserMapper.applyUpdate(e, dto, encoded);
        try {
            return UserMapper.toResponse(repository.save(e));
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw ex;
        }
    }

    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("User not found: " + id);
        }
        repository.deleteById(id);
    }
}
