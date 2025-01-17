package com.coffeecode.model.json.schema;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coffeecode.model.json.exception.JsonValidationException;
import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.schema.ValidationMessage;

@ExtendWith(MockitoExtension.class)
class SchemaValidatorTest {

    @Mock
    private SchemaValidatable mockSchema;
    private SchemaValidator validator;

    @BeforeEach
    void setUp() {
        validator = new SchemaValidator(mockSchema);
    }

    @Test
    void validate_ValidJson_NoErrors() {
        JsonNode mockNode = mock(JsonNode.class);
        when(mockSchema.validate(mockNode)).thenReturn(Set.of());
        Set<ValidationMessage> errors = validator.validate(mockNode);
        assertTrue(errors.isEmpty());
    }

    @Test
    void validate_InvalidJson_ThrowsException() {
        JsonNode mockNode = mock(JsonNode.class);
        ValidationMessage mockError = mock(ValidationMessage.class);
        when(mockError.getMessage()).thenReturn("Test error");
        when(mockSchema.validate(mockNode)).thenReturn(Set.of(mockError));
        JsonValidationException exception = assertThrows(
                JsonValidationException.class,
                () -> validator.validate(mockNode)
        );
        assertTrue(exception.getMessage().contains("Test error"));
    }
}
