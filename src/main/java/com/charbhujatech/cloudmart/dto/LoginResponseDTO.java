package com.charbhujatech.cloudmart.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter @Setter
public class LoginResponseDTO {

    public String jwtToken;

    public HttpStatus httpStatus;
}
