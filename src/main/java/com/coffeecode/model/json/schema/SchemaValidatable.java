package com.coffeecode.model.json.schema;

import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.schema.ValidationMessage;

public interface SchemaValidatable {
    Set<ValidationMessage> validate(JsonNode node);
}