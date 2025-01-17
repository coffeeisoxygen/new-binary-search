package com.coffeecode.model.json;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coffeecode.config.AppConfig;
import com.coffeecode.model.Vocabulary;
import com.coffeecode.model.json.resources.ResourceLoader;
import com.coffeecode.model.json.schema.SchemaValidatable;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class JsonServicesTest {

    @Mock
    private AppConfig mockConfig;
    @Mock
    private ObjectMapper mockMapper;
    @Mock
    private SchemaValidatable mockSchema;
    @Mock
    private ResourceLoader mockResourceLoader;

    private JsonServices jsonServices;

    @BeforeEach
    void setUp() {
        jsonServices = new JsonServices(
                mockConfig,
                mockMapper,
                mockSchema,
                mockResourceLoader
        );
    }

    @Test
    void parseFile_ValidJson_ReturnsVocabularyList() throws Exception {
        // Arrange
        String json = "[{\"english\":\"apple\",\"indonesian\":\"apel\"}]";
        List<Vocabulary> expected = List.of(new Vocabulary("apple", "apel"));
        InputStream mockStream = new ByteArrayInputStream(json.getBytes());

        when(mockConfig.getVocabularyPath()).thenReturn("/test/path");
        when(mockResourceLoader.getResourceAsStream(anyString())).thenReturn(mockStream);
        when(mockMapper.readTree(json)).thenReturn(mock(JsonNode.class));
        when(mockSchema.validate(any())).thenReturn(Set.of());
        when(mockMapper.readValue(eq(json), any(TypeReference.class))).thenReturn(expected);

        // Act
        List<Vocabulary> result = jsonServices.parseFile(null);

        // Assert
        assertEquals(expected, result);
    }
}
