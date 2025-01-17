package com.coffeecode.model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void testTranslateEnglishToIndonesian() {
        assertEquals("halo", dictionary.translate("hello", Language.ENGLISH));
        assertEquals("dunia", dictionary.translate("world", Language.ENGLISH));
        assertEquals("kopi", dictionary.translate("coffee", Language.ENGLISH));
        assertNull(dictionary.translate("nonexistent", Language.ENGLISH));
    }

    @Test
    void testTranslateIndonesianToEnglish() {
        assertEquals("hello", dictionary.translate("halo", Language.INDONESIAN));
        assertEquals("world", dictionary.translate("dunia", Language.INDONESIAN));
        assertEquals("coffee", dictionary.translate("kopi", Language.INDONESIAN));
        assertNull(dictionary.translate("tidak ada", Language.INDONESIAN));
    }

    @Test
    void testTranslateWithNull() {
        assertNull(dictionary.translate(null, Language.ENGLISH));
        assertNull(dictionary.translate(null, Language.INDONESIAN));
    }

    @Test
    void testTranslateWithEmptyString() {
        assertNull(dictionary.translate("", Language.ENGLISH));
        assertNull(dictionary.translate("", Language.INDONESIAN));
    }

    @Test
    void testTranslateWithBlankString() {
        assertNull(dictionary.translate("   ", Language.ENGLISH));
        assertNull(dictionary.translate("   ", Language.INDONESIAN));
    }

    @Test
    void testSize() {
        assertEquals(3, dictionary.size());
    }
}
