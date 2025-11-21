package com.poseidon.mapper;

import com.poseidon.domain.User;
import com.poseidon.dto.user.UserCreateRequest;
import com.poseidon.dto.user.UserResponse;
import com.poseidon.dto.user.UserUpdateRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @Test
    void applyCreate_shouldMapAllFieldsAndSetPassword() {
        User user = new User();
        UserCreateRequest request = new UserCreateRequest("username", "RawPass1!", "Full Name", "ROLE_USER");
        String encodedPassword = "encodedPassword123";

        UserMapper.applyCreate(user, request, encodedPassword);

        assertEquals("username", user.getUsername());
        assertEquals("encodedPassword123", user.getPassword()); 
        assertEquals("Full Name", user.getFullname());
        assertEquals("ROLE_USER", user.getRole());
    }

    @Test
    void applyUpdate_shouldUpdateFields_AndIgnorePasswordIfNull() {
        User user = new User();
        user.setUsername("old");
        user.setPassword("oldPass");
        
        UserUpdateRequest request = new UserUpdateRequest("new", null, "New Name", "ROLE_ADMIN");

        UserMapper.applyUpdate(user, request, null);

        assertEquals("new", user.getUsername());
        assertEquals("oldPass", user.getPassword()); 
        assertEquals("New Name", user.getFullname());
        assertEquals("ROLE_ADMIN", user.getRole());
    }

    @Test
    void applyUpdate_shouldUpdatePassword_IfProvided() {
        User user = new User();
        user.setPassword("oldPass");
        
        UserUpdateRequest request = new UserUpdateRequest("u", "NewRaw", "f", "r");
        String encodedNewPass = "newEncodedPass";

        UserMapper.applyUpdate(user, request, encodedNewPass);

        assertEquals("newEncodedPass", user.getPassword());
    }

    @Test
    void toResponse_shouldMapToDto() {
        User user = User.builder()
                .id(1)
                .username("User")
                .fullname("Full")
                .role("ROLE")
                .build();

        UserResponse response = UserMapper.toResponse(user);

        assertEquals(1, response.id());
        assertEquals("User", response.username());
        assertEquals("Full", response.fullname());
        assertEquals("ROLE", response.role());
    }
}