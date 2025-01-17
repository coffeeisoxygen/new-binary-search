package com.coffeecode.model.json;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coffeecode.config.AppConfig;
import com.coffeecode.model.Vocabulary;
import com.coffeecode.model.json.resources.ResourceLoader;
import com.coffeecode.model.json.schema.SchemaValidatable;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.ValidationMessage;

@ExtendWith(MockitoExtension.class)
class JsonServicesTest {
    private JsonServices jsonServices;
    private final String TEST_JSON = "[{\"english\":\"apple\",\"indonesian\":\"apel\"}]";
    
    @Mock private ResourceLoader mockResourceLoader;
    private final AppConfig config = AppConfig.getDefault(); // Use real config
    private final ObjectMapper mapper = new ObjectMapper(); // Use real mapper
    
    @BeforeEach
    void setUp() {
        SchemaValidatable schema = new TestSchemaValidatable(); // Use test implementation
        jsonServices = new JsonServices(config, mapper, schema, mockResourceLoader);
    }

    @Test
    void parseFile_ValidJson_ReturnsVocabularyList() throws Exception {
        // Arrange
        InputStream mockStream = new ByteArrayInputStream(TEST_JSON.getBytes());
        when(mockResourceLoader.getResourceAsStream(anyString())).thenReturn(mockStream);

        // Act
        List<Vocabulary> result = jsonServices.parseFile(null);

        // Assert
        assertEquals("apple", result.get(0).english());
        assertEquals("apel", result.get(0).indonesian());
    }

    // Test schema implementation
    private static class TestSchemaValidatable implements SchemaValidatable {
        @Override
        public Set<ValidationMessage> validate(JsonNode node) {
            return Collections.emptySet();
        }
    }
}
