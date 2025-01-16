package com.coffeecode.model.record;

public record Vocabulary(String english, String indonesian) {

    private static final String SEARCH_DUMMY = "__SEARCH__";

    public Vocabulary  {
        if ((english == null || english.isBlank()) && !indonesian.equals(SEARCH_DUMMY)) {
            throw new IllegalArgumentException("English word cannot be null or blank");
        }
        if ((indonesian == null || indonesian.isBlank()) && (english == null || !english.equals(SEARCH_DUMMY))) {
            throw new IllegalArgumentException("Indonesian word cannot be null or blank");
        }
    }

    public static Vocabulary searchByEnglish(String english) {
        return new Vocabulary(english.toLowerCase(), SEARCH_DUMMY);
    }

    public static Vocabulary searchByIndonesian(String indonesian) {
        return new Vocabulary(SEARCH_DUMMY, indonesian.toLowerCase());
    }
}
