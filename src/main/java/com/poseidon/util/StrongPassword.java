package com.poseidon.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Strong password validator annotation:
 * - at least 8 characters
 * - at least one uppercase letter
 * - at least one digit
 * - at least one symbol
 */
@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = StrongPasswordValidator.class)
@Documented
public @interface StrongPassword {
    String message() default "Password must be at least 8 chars, contain an uppercase, a digit and a symbol";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
