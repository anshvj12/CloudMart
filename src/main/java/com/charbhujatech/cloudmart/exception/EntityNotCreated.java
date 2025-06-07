package com.charbhujatech.cloudmart.exception;

public class EntityNotCreated extends RuntimeException {
    public EntityNotCreated(String userEntityIsNotCreated) {
        super(userEntityIsNotCreated);
    }
}
