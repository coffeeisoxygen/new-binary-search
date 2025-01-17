package com.coffeecode.model.json.exception;

public class JsonValidationException extends JsonParsingException {

    public JsonValidationException(String message) {
        super(message);
    }

    public JsonValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
