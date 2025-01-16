package com.coffeecode.model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.coffeecode.model.record.Vocabulary;

class DictionaryTest {

    private Dictionary dictionary;

    @BeforeEach
    void setUp() {
        List<Vocabulary> vocabularies = Arrays.asList(
                new Vocabulary("hello", "halo"),
                new Vocabulary("world", "dunia"),
                new Vocabulary("coffee", "kopi")
        );
        dictionary = new Dictionary(vocabularies);
    }

    @Test
    void testFindIndonesian() {
        assertEquals("halo", dictionary.findIndonesian("hello"));
        assertEquals("dunia", dictionary.findIndonesian("world"));
        assertEquals("kopi", dictionary.findIndonesian("coffee"));
        assertNull(dictionary.findIndonesian("nonexistent"));
    }

    @Test
    void testFindEnglish() {
        assertEquals("hello", dictionary.findEnglish("halo"));
        assertEquals("world", dictionary.findEnglish("dunia"));
        assertEquals("coffee", dictionary.findEnglish("kopi"));
        assertNull(dictionary.findEnglish("tidak ada"));
    }

    @Test
    void testFindIndonesianWithNull() {
        assertNull(dictionary.findIndonesian(null));
    }

    @Test
    void testFindEnglishWithNull() {
        assertNull(dictionary.findEnglish(null));
    }

    @Test
    void testFindIndonesianWithEmptyString() {
        assertNull(dictionary.findIndonesian(""));
    }

    @Test
    void testFindEnglishWithEmptyString() {
        assertNull(dictionary.findEnglish(""));
    }

    @Test
    void testSize() {
        assertEquals(3, dictionary.size());
    }
}
