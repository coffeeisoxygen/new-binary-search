package com.coffeecode.model.json.schema;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;

import com.coffeecode.model.json.exception.JsonValidationException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.ValidationMessage;

@ExtendWith(MockitoExtension.class)
class SchemaValidatorTest {

    @Mock
    private SchemaValidatable mockSchema;
    @Mock
    private ValidationMessage mockMessage;
    
    private SchemaValidator validator;
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        validator = new SchemaValidator(mockSchema);
        mapper = new ObjectMapper();
    }

    @Test
    void validate_ValidJson_NoErrors() throws Exception {
        // Arrange
        JsonNode validNode = mapper.readTree("{\"test\":\"value\"}");
        when(mockSchema.validate(validNode)).thenReturn(Collections.emptySet());

        // Act
        Set<ValidationMessage> errors = validator.validate(validNode);

        // Assert
        assertTrue(errors.isEmpty());
    }

    @Test
    void validate_InvalidJson_ThrowsException() throws Exception {
        // Arrange
        JsonNode invalidNode = mapper.readTree("{\"test\":\"value\"}");
        when(mockMessage.getMessage()).thenReturn("Test error");
        when(mockSchema.validate(invalidNode))
            .thenReturn(Collections.singleton(mockMessage));

        // Act & Assert
        JsonValidationException ex = assertThrows(
            JsonValidationException.class,
            () -> validator.validate(invalidNode)
        );
        assertTrue(ex.getMessage().contains("Test error"));
    }
}
