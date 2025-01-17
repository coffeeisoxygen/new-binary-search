package com.coffeecode.model.json.schema;

import java.util.Set;

import com.coffeecode.model.json.exception.JsonValidationException;
import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.ValidationMessage;

public class SchemaValidator {

    private final JsonSchema schema;

    public SchemaValidator(JsonSchema schema) {
        this.schema = schema;
    }

    public Set<ValidationMessage> validate(JsonNode jsonNode) {
        Set<ValidationMessage> errors = schema.validate(jsonNode);
        if (!errors.isEmpty()) {
            throw new JsonValidationException(errors.toString());
        }
        return errors;
    }

}
