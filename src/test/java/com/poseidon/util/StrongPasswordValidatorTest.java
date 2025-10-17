package com.poseidon.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StrongPasswordValidatorTest {

    @Test
    void valid_password() {
        StrongPasswordValidator v = new StrongPasswordValidator();
        assertTrue(v.isValid("Admin#2025!", null));
    }

    @Test
    void invalid_password() {
        StrongPasswordValidator v = new StrongPasswordValidator();
        assertFalse(v.isValid("weakpass", null));
    }
}
