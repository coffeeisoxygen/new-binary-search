package com.coffeecode.model.json;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.schema.JsonSchema;
import com.coffeecode.model.json.SchemaValidator;

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