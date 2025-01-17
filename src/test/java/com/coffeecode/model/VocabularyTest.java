package com.coffeecode.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.coffeecode.validation.ValidationException;
import com.coffeecode.model.Language;

class VocabularyTest {

    @Test
    void constructor_ValidInput_CreatesVocabulary() {
        Vocabulary vocab = new Vocabulary("hello", "halo");
        assertEquals("hello", vocab.english());
        assertEquals("halo", vocab.indonesian());
    }

    @Test
    void constructor_NullEnglish_ThrowsException() {
        assertThrows(ValidationException.class, () -> new Vocabulary(null, "halo"));
    }

    @Test
    void searchByLanguage_ValidInput_CreatesSearchTerm() {
        Vocabulary searchTerm = Vocabulary.searchByLanguage("hello", Language.ENGLISH);
        assertTrue(searchTerm.isSearchTerm());
        assertEquals("hello", searchTerm.english());
    }
}
