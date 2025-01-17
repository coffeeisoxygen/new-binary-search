package com.coffeecode.model.core;

import com.coffeecode.validation.ValidationException;
import com.coffeecode.validation.WordValidator;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Immutable record representing a bilingual vocabulary entry. Supports JSON
 * serialization/deserialization and search operations.
 */
public record Vocabulary(
        @JsonProperty(value = "english", required = true)
        String english,
        @JsonProperty(value = "indonesian", required = true)
        String indonesian) {

    private static final String SEARCH_DUMMY = "__SEARCH__";

    @JsonCreator
    public Vocabulary(String english, String indonesian) {
        if (english == null) {
            throw new ValidationException("English cannot be null or empty");
        }
        if (indonesian == null) {
            throw new ValidationException("Indonesian cannot be null or empty");
        }
        if (!isSearchTerm(english, indonesian)) {
            WordValidator.validateWord(english, "English");
            WordValidator.validateWord(indonesian, "Indonesian");
        }
        this.english = SEARCH_DUMMY.equals(english) ? english : WordValidator.sanitizeWord(english);
        this.indonesian = SEARCH_DUMMY.equals(indonesian) ? indonesian : WordValidator.sanitizeWord(indonesian);
    }

    public static Vocabulary searchByLanguage(String word, Language language) {
        WordValidator.validateWord(word, language.name());
        return language == Language.ENGLISH
                ? new Vocabulary(word, SEARCH_DUMMY)
                : new Vocabulary(SEARCH_DUMMY, word);
    }

    @JsonIgnore
    public String getWord(Language language) {
        return language == Language.ENGLISH ? english : indonesian;
    }

    @JsonIgnore
    public boolean isSearchTerm() {
        return english.equals(SEARCH_DUMMY) || indonesian.equals(SEARCH_DUMMY);
    }

    private static boolean isSearchTerm(String english, String indonesian) {
        return english.equals(SEARCH_DUMMY) ^ indonesian.equals(SEARCH_DUMMY);
    }

    @Override
    public String toString() {
        if (isSearchTerm()) {
            return "SearchTerm["
                    + (english.equals(SEARCH_DUMMY) ? indonesian : english)
                    + "]";
        }
        return "Vocabulary[english=" + english + ", indonesian=" + indonesian + "]";
    }
}
// TODO: Talk Later About deserialization and serialization
