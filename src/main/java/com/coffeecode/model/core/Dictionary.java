package com.coffeecode.model.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dictionary {

    private final List<Vocabulary> englishToIndonesian;
    private final List<Vocabulary> indonesianToEnglish;

    public Dictionary(List<Vocabulary> vocabularies) {
        validateInput(vocabularies);
        this.englishToIndonesian = new ArrayList<>(vocabularies);
        this.indonesianToEnglish = new ArrayList<>(vocabularies);
        initializeSortedLists();
    }

    public List<Vocabulary> getSearchList(Language sourceLanguage) {
        return sourceLanguage == Language.ENGLISH
                ? englishToIndonesian : indonesianToEnglish;
    }

    private void initializeSortedLists() {
        Collections.sort(englishToIndonesian,
                (a, b) -> a.getWord(Language.ENGLISH).compareToIgnoreCase(b.getWord(Language.ENGLISH)));
        Collections.sort(indonesianToEnglish,
                (a, b) -> a.getWord(Language.INDONESIAN).compareToIgnoreCase(b.getWord(Language.INDONESIAN)));
    }

    public int size() {
        return englishToIndonesian.size();
    }

    private void validateInput(List<Vocabulary> vocabularies) {
        if (vocabularies == null || vocabularies.isEmpty()) {
            throw new IllegalArgumentException("Vocabulary list cannot be null or empty");
        }
    }
}
