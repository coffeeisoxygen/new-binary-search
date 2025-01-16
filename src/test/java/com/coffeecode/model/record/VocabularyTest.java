package com.coffeecode.model.record;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class VocabularyTest {

    @Test
    void testValidVocabulary() {
        Vocabulary vocabulary = new Vocabulary("hello", "halo");
        assertEquals("hello", vocabulary.english());
        assertEquals("halo", vocabulary.indonesian());
    }

    @Test
    void testNullEnglishWord() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Vocabulary(null, "halo");
        });
        assertEquals("English word cannot be null or blank", exception.getMessage());
    }

    @Test
    void testBlankEnglishWord() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Vocabulary(" ", "halo");
        });
        assertEquals("English word cannot be null or blank", exception.getMessage());
    }

    @Test
    void testNullIndonesianWord() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Vocabulary("hello", null);
        });
        assertEquals("Indonesian word cannot be null or blank", exception.getMessage());
    }

    @Test
    void testBlankIndonesianWord() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Vocabulary("hello", " ");
        });
        assertEquals("Indonesian word cannot be null or blank", exception.getMessage());
    }
}
