package com.coffeecode.model.json.schema;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.schema.JsonSchema;

@ExtendWith(MockitoExtension.class)
class SchemaValidatorTest {
    @Mock private JsonSchema mockSchema;
    private SchemaValidator validator;

    @BeforeEach
    void setUp() {
        validator = new SchemaValidator(mockSchema);
    }

    @Test
    void validate_ValidJson_NoErrors() {
        // Arrange
        JsonNode mockNode = mock(JsonNode.class);
        when(mockSchema.validate(mockNode)).thenReturn(Set.of());

        // Act & Assert
        assertDoesNotThrow(() -> validator.validate(mockNode));
    }
}