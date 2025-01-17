package com.coffeecode.model.json.schema;

import java.util.Set;

import com.coffeecode.model.json.exception.JsonValidationException;
import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.schema.ValidationMessage;

public class SchemaValidator {

    private final SchemaValidatable schema;

    public SchemaValidator(SchemaValidatable schema) {
        this.schema = schema;
    }

    public Set<ValidationMessage> validate(JsonNode jsonNode) {
        if (jsonNode == null) {
            throw new IllegalArgumentException("jsonNode cannot be null");
        }
        Set<ValidationMessage> errors = schema.validate(jsonNode);
        if (!errors.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Validation errors: ");
            for (ValidationMessage error : errors) {
                errorMessage.append(error.toString()).append("; ");
            }
            throw new JsonValidationException(errorMessage.toString());
        }
        return errors;
    }

}
