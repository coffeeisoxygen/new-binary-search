package com.coffeecode.model.json.parser;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coffeecode.config.AppConfig;
import com.coffeecode.model.Vocabulary;
import com.coffeecode.model.json.JsonServices;
import com.coffeecode.model.json.resources.ResourceLoader;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;

@ExtendWith(MockitoExtension.class)
class JsonParserTest {

    @Mock
    private ObjectMapper mockMapper;
    private JsonServices parser;

    @BeforeEach
        void setUp() {
            AppConfig mockConfig = mock(AppConfig.class);
            JsonSchema mockSchema = mock(JsonSchema.class);
            ResourceLoader mockResourceLoader = mock(ResourceLoader.class);
            parser = new JsonServices(mockConfig, mockMapper, mockSchema, mockResourceLoader);
        }

    @Test
    void parse_ValidJson_ReturnsVocabularyList() throws Exception {
        String json = "[{\"english\":\"apple\",\"indonesian\":\"apel\"}]";
        List<Vocabulary> expected = List.of(new Vocabulary("apple", "apel"));
        when(mockMapper.readValue(eq(json), ArgumentMatchers.<TypeReference<List<Vocabulary>>>any()))
                .thenReturn(expected);
        assertEquals(expected, parser.parse(json));
    }
}
