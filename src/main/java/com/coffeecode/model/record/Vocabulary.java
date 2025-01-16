package com.coffeecode.model.record;

public record Vocabulary(String english, String indonesian) {

    public Vocabulary  {
        if (english == null || english.isBlank()) {
            throw new IllegalArgumentException("English word cannot be null or blank");
        }
        if (indonesian == null || indonesian.isBlank()) {
            throw new IllegalArgumentException("Indonesian word cannot be null or blank");
        }
    }
}
