package com.ynov.fantasy_war.domain;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}