package com.coffeecode.model.json;

import java.io.InputStream;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mock;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.coffeecode.config.AppConfig;
import com.coffeecode.model.Vocabulary;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.networknt.schema.JsonSchema;

@ExtendWith(MockitoExtension.class)
class JsonServicesTest {

    private static final String TEST_SCHEMA_PATH = "/data/schema/vocabulary-schema.json";
    private static final String TEST_VOCAB_PATH = "/data/vocabularies.json";

    @Mock
    private AppConfig mockConfig;
    @Mock
    private ObjectMapper mockObjectMapper;
    @Mock
    private JsonSchema mockSchema;
    @Mock
    private TypeFactory mockTypeFactory;
    @Mock
    private InputStream mockInputStream;

    private JsonServices jsonServices;
    private String validJson;

    @BeforeEach
    void setUp() {
        lenient().when(mockObjectMapper.getTypeFactory()).thenReturn(mockTypeFactory);
        lenient().when(mockConfig.getSchemaPath()).thenReturn(TEST_SCHEMA_PATH);

        jsonServices = spy(new JsonServices(mockConfig));
        ReflectionTestUtils.setField(jsonServices, "objectMapper", mockObjectMapper);
        ReflectionTestUtils.setField(jsonServices, "schema", mockSchema);

        validJson = "[{\"english\":\"apple\",\"indonesian\":\"apel\"}]";
    }

    @Test
    void parseFile_ValidPath_ReturnsVocabularyList() throws Exception {
        // Arrange
        List<Vocabulary> expected = List.of(new Vocabulary("apple", "apel"));
        doReturn(mockInputStream).when(jsonServices).getResourceAsStream(anyString());
        when(mockInputStream.readAllBytes()).thenReturn(validJson.getBytes());

        JsonNode mockNode = mock(JsonNode.class);
        when(mockObjectMapper.readTree(validJson)).thenReturn(mockNode);
        when(mockSchema.validate(mockNode)).thenReturn(Set.of());

        // Perbaikan: Gunakan eq() dengan TypeReference yang spesifik
        when(mockObjectMapper.readValue(eq(validJson), any(TypeReference.class)))
                .thenReturn(expected);
        // Act
        List<Vocabulary> result = jsonServices.parseFile(TEST_VOCAB_PATH);

        // Assert
        assertNotNull(result);
        assertEquals("apple", result.get(0).english());
    }

    @Test
    void parseFile_InvalidJson_ThrowsException() throws Exception {
        // Arrange
        doReturn(mockInputStream).when(jsonServices).getResourceAsStream(anyString());
        when(mockInputStream.readAllBytes())
                .thenReturn("invalid json".getBytes());
        when(mockObjectMapper.readTree("invalid json"))
                .thenThrow(new JsonProcessingException("Invalid JSON") {
                });

        // Act & Assert
        assertThrows(JsonParsingException.class,
                () -> jsonServices.parseFile(TEST_VOCAB_PATH));
    }
}
