package com.coffeecode.validation;

public class WordValidator {

    private WordValidator() {
        throw new IllegalStateException("Utility class");
    }

    private static final int MAX_WORD_LENGTH = 100;

    public static void validateWord(String word, String fieldName) {
        if (word == null || word.isBlank()) {
            throw new ValidationException(fieldName + " cannot be null or empty");
        }
        if (word.length() > MAX_WORD_LENGTH) {
            throw new ValidationException(fieldName + " exceeds maximum length of " + MAX_WORD_LENGTH);
        }
    }

    public static boolean isValidWord(String word) {
        return word != null
                && !word.isBlank()
                && word.length() <= MAX_WORD_LENGTH;
    }

    public static String sanitizeWord(String word) {
        if (word == null) {
            return null;
        }
        return word.trim()
                .toLowerCase()
                .replaceAll("[^a-zA-Z\\s-]", "")
                .replaceAll("\\s+", " ");
    }
}
