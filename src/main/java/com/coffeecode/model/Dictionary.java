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
                (a, b) -> a.getWord(Language.ENGLISH).compareToIgnoreCase(b.getWord(Language.ENGLISH)));
        Collections.sort(indonesianToEnglish,
                (a, b) -> a.getWord(Language.INDONESIAN).compareToIgnoreCase(b.getWord(Language.INDONESIAN)));

        logger.info("Dictionary initialized with {} entries", vocabularies.size());
    }

    public String translate(String word, Language sourceLanguage) {
        if (word == null || word.isBlank()) {
            logger.warn("Translation term cannot be null or empty");
            return null;
        }

        List<Vocabulary> searchList = sourceLanguage == Language.ENGLISH
                ? englishToIndonesian : indonesianToEnglish;

        int index = Collections.binarySearch(searchList,
                Vocabulary.searchByLanguage(word, sourceLanguage),
                (a, b) -> a.getWord(sourceLanguage).compareToIgnoreCase(b.getWord(sourceLanguage)));

        if (index >= 0) {
            Vocabulary found = searchList.get(index);
            String translation = sourceLanguage == Language.ENGLISH
                    ? found.indonesian() : found.english();
            logger.debug("Found translation for {} word '{}': '{}'",
                    sourceLanguage, word, translation);
            return translation;
        }

        logger.debug("No translation found for {} word: {}", sourceLanguage, word);
        return null;
    }

    public int size() {
        return englishToIndonesian.size();
    }
}
