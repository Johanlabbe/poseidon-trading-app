package com.poseidon.service;

import com.poseidon.dto.user.UserCreateRequest;
import com.poseidon.dto.user.UserResponse;
import com.poseidon.dto.user.UserUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserResponse create(UserCreateRequest dto);
    Page<UserResponse> findAll(Pageable pageable);
    UserResponse findById(Integer id);
    UserResponse update(Integer id, UserUpdateRequest dto);
    void delete(Integer id);
}
