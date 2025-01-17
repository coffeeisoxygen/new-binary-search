package com.coffeecode.model.json;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import org.mockito.Mock;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.coffeecode.config.AppConfig;
import com.coffeecode.model.Vocabulary;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private InputStream mockInputStream;

    private JsonServices jsonServices;
    private String validJson;

    @BeforeEach
    void setUp() {
        jsonServices = spy(new JsonServices(mockConfig));
        ReflectionTestUtils.setField(jsonServices, "objectMapper", mockObjectMapper);
        ReflectionTestUtils.setField(jsonServices, "schema", mockSchema);
        validJson = "[{\"english\":\"apple\",\"indonesian\":\"apel\"}]";
    }

    @Test
    void parseFile_ValidPath_ReturnsVocabularyList() throws Exception {
        // Arrange
        when(mockConfig.getVocabularyPath()).thenReturn(TEST_VOCAB_PATH);
        doReturn(mockInputStream).when(jsonServices).getResourceAsStream(anyString());
        when(mockInputStream.readAllBytes()).thenReturn(validJson.getBytes());

        JsonNode mockNode = mock(JsonNode.class);
        when(mockObjectMapper.readTree(validJson)).thenReturn(mockNode);
        when(mockSchema.validate(mockNode)).thenReturn(Set.of());

        List<Vocabulary> expected = List.of(new Vocabulary("apple", "apel"));
        TypeReference<List<Vocabulary>> typeRef = new TypeReference<>() {
        };
        when(mockObjectMapper.readValue(eq(validJson), eq(typeRef))).thenReturn(expected);

        // Act
        List<Vocabulary> result = jsonServices.parseFile(TEST_VOCAB_PATH);

        // Assert
        assertNotNull(result);
        assertEquals("apple", result.get(0).english());
    }

    @Test
    void parseFile_InvalidJson_ThrowsException() throws Exception {
        // Arrange
        when(mockConfig.getVocabularyPath()).thenReturn(TEST_VOCAB_PATH);
        String invalidJson = "invalid json content";
        doReturn(mockInputStream).when(jsonServices).getResourceAsStream(anyString());
        when(mockInputStream.readAllBytes()).thenReturn(invalidJson.getBytes());
        when(mockObjectMapper.readTree(invalidJson))
                .thenThrow(new JsonParsingException("Invalid JSON"));

        // Act & Assert
        assertThrows(JsonParsingException.class,
                () -> jsonServices.parseFile(TEST_VOCAB_PATH));
    }

    @Test
    void parseJson_ValidInput_ReturnsVocabularyList() throws JsonProcessingException {
        // Setup
        List<Vocabulary> expected = Arrays.asList(
                new Vocabulary("cat", "kucing"),
                new Vocabulary("dog", "anjing")
        );
        String validJson = "[{\"english\":\"cat\",\"indonesian\":\"kucing\"},{\"english\":\"dog\",\"indonesian\":\"anjing\"}]";

        // Create specific TypeReference for List<Vocabulary>
        TypeReference<List<Vocabulary>> typeRef = new TypeReference<>() {
        };

        // Use specific TypeReference in mock
        when(mockObjectMapper.readValue(eq(validJson), same(typeRef)))
                .thenReturn(expected);

        // Test
        List<Vocabulary> result = jsonServices.parseJson(validJson);

        // Verify
        assertEquals(expected, result);
        verify(mockObjectMapper).readValue(validJson, typeRef);
    }
}
