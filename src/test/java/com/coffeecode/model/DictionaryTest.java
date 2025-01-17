package com.coffeecode.model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.coffeecode.model.core.Dictionary;
import com.coffeecode.model.core.Language;
import com.coffeecode.model.core.Vocabulary;

class DictionaryTest {

    private Dictionary dictionary;
    private List<Vocabulary> testData;

    @BeforeEach
    void setUp() {
        testData = Arrays.asList(
                new Vocabulary("cat", "kucing"),
                new Vocabulary("dog", "anjing"),
                new Vocabulary("bird", "burung")
        );
        dictionary = new Dictionary(testData);
    }

    @Test
    void constructor_NullInput_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Dictionary(null));
    }

    @Test
    void getSearchList_English_ReturnsSortedList() {
        List<Vocabulary> result = dictionary.getSearchList(Language.ENGLISH);
        assertEquals("bird", result.get(0).english());
        assertEquals("cat", result.get(1).english());
        assertEquals("dog", result.get(2).english());
    }
}
