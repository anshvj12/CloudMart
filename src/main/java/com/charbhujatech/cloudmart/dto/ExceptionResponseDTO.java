package com.charbhujatech.cloudmart.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@RequiredArgsConstructor
public class ExceptionResponseDTO  {

    private String apiPath;

    private HttpStatus statusCode;

    private String message;
}
