package com.charbhujatech.cloudmart.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserResponseDTO {

    private Long userId;

    private String firstName;

    private String lastName;

    private String password;

    private String enterAgainPassword;

    private String email;

    private String phone;

    private String role;
}
