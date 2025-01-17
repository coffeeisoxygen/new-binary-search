package com.coffeecode.model.search;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.coffeecode.model.Language;
import com.coffeecode.model.Vocabulary;

class BinarySearchStrategyTest {
    private SearchStrategy searchStrategy;
    private List<Vocabulary> sortedList;

    @BeforeEach
    void setUp() {
        searchStrategy = new BinarySearchStrategy();
        sortedList = Arrays.asList(
            new Vocabulary("apple", "apel"),
            new Vocabulary("cat", "kucing"),
            new Vocabulary("dog", "anjing")
        );
    }

    @Test
    void findTranslation_ExistingWord_ReturnsTranslation() {
        String result = searchStrategy.findTranslation(
            sortedList, "cat", Language.ENGLISH, Language.INDONESIAN);
        assertEquals("kucing", result);
    }

    @Test
    void findTranslation_NonExistentWord_ReturnsNull() {
        String result = searchStrategy.findTranslation(
            sortedList, "zebra", Language.ENGLISH, Language.INDONESIAN);
        assertNull(result);
    }
}
