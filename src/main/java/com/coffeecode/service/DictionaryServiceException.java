package com.coffeecode.service;

public class DictionaryServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DictionaryServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public DictionaryServiceException(String message) {
        super(message);
    }

}
