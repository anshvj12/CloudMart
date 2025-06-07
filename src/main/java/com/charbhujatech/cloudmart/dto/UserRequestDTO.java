package com.charbhujatech.cloudmart.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {

    @NotEmpty(message = "First Name can not empty")
    private String firstName;

    private String lastName;

    @NotEmpty(message = "Please enter password")
    private String password;

    @NotEmpty(message = "Please re-enter password")
    private String enterAgainPassword;

    @Email(message = "Enter a valid email")
    @NotEmpty(message = "Please enter email")
    private String email;

    @NotEmpty(message = "Please enter phone number")
    //@Pattern(regexp = "$|[0-9]{10}", message = "Mobile number must be 10 digit and Number value only.")
    private String phone;

    @NotEmpty(message = "Please enter role")
    private String role;

}
