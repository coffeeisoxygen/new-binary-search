package com.coffeecode.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class VocabularyTest {

    @Test
    public void testValidVocabulary() {
        Vocabulary vocabulary = new Vocabulary("hello", "halo");
        assertEquals("hello", vocabulary.english());
        assertEquals("halo", vocabulary.indonesian());
    }

    @Test
    public void testNullEnglishWord() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Vocabulary(null, "halo");
        });
        assertEquals("English word cannot be null or blank", exception.getMessage());
    }

    @Test
    public void testBlankEnglishWord() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Vocabulary(" ", "halo");
        });
        assertEquals("English word cannot be null or blank", exception.getMessage());
    }

    @Test
    public void testNullIndonesianWord() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Vocabulary("hello", null);
        });
        assertEquals("Indonesian word cannot be null or blank", exception.getMessage());
    }

    @Test
    public void testBlankIndonesianWord() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Vocabulary("hello", " ");
        });
        assertEquals("Indonesian word cannot be null or blank", exception.getMessage());
    }
}
