package ru.itmo.wp.model.exception;

public class ConfirmationException extends RuntimeException {

    public ConfirmationException(String message) {
        super(message);
    }

    public ConfirmationException(String message, Throwable cause) {
        super(message, cause);
    }
}
