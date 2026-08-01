package dev.jakubw.exception;

public class SignUpException extends RuntimeException {
    public SignUpException(String message, Throwable cause) {
        super(message,cause);
    }
    public SignUpException(String message) {
        super(message);
    }
}
