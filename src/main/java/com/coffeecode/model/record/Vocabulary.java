package com.coffeecode.model.record;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.model.Language;

/**
 * Represents a bilingual vocabulary entry containing English and Indonesian
 * words.
 */
public record Vocabulary(String english, String indonesian) {

    private static final Logger logger = LoggerFactory.getLogger(Vocabulary.class);
    private static final String SEARCH_DUMMY = "__SEARCH__";
    private static final int MAX_WORD_LENGTH = 100;

    public Vocabulary  {
        validateWord(english, "English", indonesian.equals(SEARCH_DUMMY));
        validateWord(indonesian, "Indonesian", english.equals(SEARCH_DUMMY));
        english = english.toLowerCase().trim();
        indonesian = indonesian.toLowerCase().trim();
    }

    private static void validateWord(String word, String language, boolean isSearchTerm) {
        if ((word == null || word.isBlank()) && !isSearchTerm) {
            logger.warn("Invalid {} word: {}", language, word);
            throw new IllegalArgumentException(language + " word cannot be null or blank");

        }
        if (word != null && word.length() > MAX_WORD_LENGTH) {
            logger.warn("{} word exceeds maximum length of {}: {}", language, MAX_WORD_LENGTH, word);
            throw new IllegalArgumentException(language + " word exceeds maximum length of " + MAX_WORD_LENGTH);
        }
    }

    public static Vocabulary searchByLanguage(String word, Language language) {
        return switch (language) {
            case ENGLISH ->
                searchByEnglish(word);
            case INDONESIAN ->
                searchByIndonesian(word);
        };
    }

    public String getWord(Language language) {
        return switch (language) {
            case ENGLISH ->
                english;
            case INDONESIAN ->
                indonesian;
        };
    }

    public boolean isSearchDummy() {
        return english.equals(SEARCH_DUMMY) || indonesian.equals(SEARCH_DUMMY);
    }

    @Override
    public String toString() {
        if (isSearchDummy()) {
            return "SearchTerm[" + (english.equals(SEARCH_DUMMY) ? indonesian : english) + "]";
        }
        return "Vocabulary[english=" + english + ", indonesian=" + indonesian + "]";
    }

    public static Vocabulary searchByEnglish(String english) {
        return new Vocabulary(english.toLowerCase(), SEARCH_DUMMY);
    }

    public static Vocabulary searchByIndonesian(String indonesian) {
        return new Vocabulary(SEARCH_DUMMY, indonesian.toLowerCase());
    }
}
