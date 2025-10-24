package com.poseidon.mapper;

import com.poseidon.domain.User;
import com.poseidon.dto.user.UserCreateRequest;
import com.poseidon.dto.user.UserResponse;
import com.poseidon.dto.user.UserUpdateRequest;

public final class UserMapper {
    private UserMapper() {}

    public static void applyCreate(User e, UserCreateRequest dto, String encodedPassword) {
        e.setUsername(dto.username());
        e.setPassword(encodedPassword);
        e.setFullname(dto.fullname());
        e.setRole(dto.role());
    }

    public static void applyUpdate(User e, UserUpdateRequest dto, String encodedPasswordOrNull) {
        e.setUsername(dto.username());
        if (encodedPasswordOrNull != null && !encodedPasswordOrNull.isBlank()) {
            e.setPassword(encodedPasswordOrNull);
        }
        e.setFullname(dto.fullname());
        e.setRole(dto.role());
    }

    public static UserResponse toResponse(User e) {
        return new UserResponse(e.getId(), e.getUsername(), e.getFullname(), e.getRole());
    }
}
