package com.coffeecode.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.model.record.Vocabulary;

public class Dictionary {

    private static final Logger logger = LoggerFactory.getLogger(Dictionary.class);
    private final List<Vocabulary> englishToIndonesian;
    private final List<Vocabulary> indonesianToEnglish;

    public Dictionary(List<Vocabulary> vocabularies) {
        this.englishToIndonesian = new ArrayList<>(vocabularies);
        this.indonesianToEnglish = new ArrayList<>(vocabularies);

        // Sort for binary search
        Collections.sort(englishToIndonesian,
                (a, b) -> a.english().compareToIgnoreCase(b.english()));
        Collections.sort(indonesianToEnglish,
                (a, b) -> a.indonesian().compareToIgnoreCase(b.indonesian()));

        logger.info("Dictionary initialized with {} entries", vocabularies.size());
    }

    // Add new method
    public String translate(String word, Language sourceLanguage) {
        if (word == null || word.isBlank()) {
            logger.warn("Translation term cannot be null or empty");
            return null;
        }

        return switch (sourceLanguage) {
            case ENGLISH ->
                findIndonesian(word);
            case INDONESIAN ->
                findEnglish(word);
        };
    }

    private String findIndonesian(String english) {
        int index = Collections.binarySearch(englishToIndonesian,
                Vocabulary.searchByEnglish(english),
                (a, b) -> a.english().compareToIgnoreCase(b.english()));

        if (index >= 0) {
            logger.debug("Found Indonesian translation for: {}", english);
            return englishToIndonesian.get(index).indonesian();
        }
        return null;
    }

    private String findEnglish(String indonesian) {
        int index = Collections.binarySearch(indonesianToEnglish,
                Vocabulary.searchByIndonesian(indonesian),
                (a, b) -> a.indonesian().compareToIgnoreCase(b.indonesian()));

        if (index >= 0) {
            logger.debug("Found English translation for: {}", indonesian);
            return indonesianToEnglish.get(index).english();
        }
        return null;
    }

    public int size() {
        return englishToIndonesian.size();
    }
}
