package com.coffeecode.model.json;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.coffeecode.model.record.Vocabulary;

class JsonServicesTest {

    private JsonServices jsonServices;

    @BeforeEach
    void setUp() {
        jsonServices = new JsonServices();
    }

    @Test
    void parseJson_ValidSingleVocabulary_ReturnsListWithOneItem() {
        // given
        String json = """
            [{"english": "hello", "indonesian": "halo"}]
            """;

        // when
        List<Vocabulary> result = jsonServices.parseJson(json);

        // then
        assertEquals(1, result.size());
        assertEquals("hello", result.get(0).english());
        assertEquals("halo", result.get(0).indonesian());
    }

    @Test
    void parseJson_ValidMultipleVocabularies_ReturnsCorrectList() {
        // given
        String json = """
            [
                {"english": "hello", "indonesian": "halo"},
                {"english": "good", "indonesian": "baik"}
            ]
            """;

        // when
        List<Vocabulary> result = jsonServices.parseJson(json);

        // then
        assertEquals(2, result.size());
        assertEquals("good", result.get(1).english());
        assertEquals("baik", result.get(1).indonesian());
    }

    @Test
    void parseJson_NullInput_ThrowsJsonParsingException() {
        // when/then
        JsonParsingException exception = assertThrows(
                JsonParsingException.class,
                () -> jsonServices.parseJson(null)
        );
        assertEquals("Invalid or empty JSON content", exception.getMessage());
    }

    @Test
    void parseJson_EmptyString_ThrowsJsonParsingException() {
        // when/then
        JsonParsingException exception = assertThrows(
                JsonParsingException.class,
                () -> jsonServices.parseJson("")
        );
        assertEquals("Invalid or empty JSON content", exception.getMessage());
    }

    @Test
    void parseJson_MalformedJson_ThrowsJsonParsingException() {
        // given
        String json = "[{\"english\": \"hello\", \"indonesian\": \"halo\""; // missing closing brackets

        // when/then
        assertThrows(JsonParsingException.class, () -> jsonServices.parseJson(json));
    }

    @Test
    void parseJson_InvalidVocabularyData_ThrowsJsonParsingException() {
        // given
        String json = """
            [{"english": null, "indonesian": "halo"}]
            """;

        // when/then
        assertThrows(JsonParsingException.class, () -> jsonServices.parseJson(json));
    }

    @Test
    void parseJson_EmptyArray_ReturnsEmptyList() {
        // given
        String json = "[]";

        // when
        List<Vocabulary> result = jsonServices.parseJson(json);

        // then
        assertTrue(result.isEmpty());
    }
}
