package com.coffeecode.model;

import com.coffeecode.util.TextUtils;

/**
 * Immutable record representing a bilingual vocabulary entry.
 */
public record Vocabulary(String english, String indonesian) {

    private static final String SEARCH_DUMMY = "__SEARCH__";
    private static final int MAX_WORD_LENGTH = 100;

    public Vocabulary  {
        if (!isValidInput(english, indonesian)) {
            throw new IllegalArgumentException(
                    String.format("Invalid vocabulary entry: english='%s', indonesian='%s'",
                            english, indonesian));
        }
        english = TextUtils.sanitizeWord(english);
        indonesian = TextUtils.sanitizeWord(indonesian);
    }

    private static boolean isValidInput(String english, String indonesian) {
        return (isValidWord(english) && isValidWord(indonesian))
                || (english.equals(SEARCH_DUMMY) ^ indonesian.equals(SEARCH_DUMMY));
    }

    private static boolean isValidWord(String word) {
        return word != null
                && !word.isBlank()
                && word.length() <= MAX_WORD_LENGTH;
    }

    public static Vocabulary searchByLanguage(String word, Language language) {
        if (word == null || word.isBlank()) {
            throw new IllegalArgumentException("Search word cannot be null or empty");
        }
        return language == Language.ENGLISH
                ? new Vocabulary(word, SEARCH_DUMMY)
                : new Vocabulary(SEARCH_DUMMY, word);
    }

    public String getWord(Language language) {
        return language == Language.ENGLISH ? english : indonesian;
    }

    public boolean isSearchTerm() {
        return english.equals(SEARCH_DUMMY) || indonesian.equals(SEARCH_DUMMY);
    }
}
