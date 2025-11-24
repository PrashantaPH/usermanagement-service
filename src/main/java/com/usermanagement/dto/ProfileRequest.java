package com.usermanagement.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequest {

    @NotBlank(message = "Name should not be empty")
    private String fullName;

    @NotBlank(message = "Role should not be empty")
    private String role;

    @Email(message = "Enter a valid email address")
    @NotNull(message = "Email should not be empty")
    private String email;

    @NotBlank(message = "Status should not be empty")
    private String status;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phone;

    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least 8 characters, include upper/lower case, number, and special character"
    )
    private String password;
}

