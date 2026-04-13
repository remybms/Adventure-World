package com.ynov.fantasy_war.domain;

public abstract class RequeteInvalideException extends RuntimeException {
    public RequeteInvalideException(String message) {
        super(message);
    }
}
