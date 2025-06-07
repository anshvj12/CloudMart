package com.charbhujatech.cloudmart.exception;

public class InvalidJWTToken extends RuntimeException {
    public InvalidJWTToken(String jwtTokenInvalid) {
        super(jwtTokenInvalid);
    }
}
