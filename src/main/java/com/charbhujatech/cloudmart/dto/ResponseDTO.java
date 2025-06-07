package com.charbhujatech.cloudmart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class ResponseDTO {

    public String statusCode;

    public String statusMessage;
}
