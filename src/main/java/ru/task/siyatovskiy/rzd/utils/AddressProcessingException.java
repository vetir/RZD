package ru.task.siyatovskiy.rzd.utils;

public class AddressProcessingException extends RuntimeException {

    public AddressProcessingException(String message) {
        super(message);
    }

    public AddressProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

}
