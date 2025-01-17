package com.coffeecode.model.core;

public enum Language {
    ENGLISH, INDONESIAN;

    /**
     * Returns the opposite language (ENGLISH ↔ INDONESIAN)
     *
     * @return The opposite language
     */
    public Language opposite() {
        return switch (this) {
            case ENGLISH ->
                INDONESIAN;
            case INDONESIAN ->
                ENGLISH;
        };
    }
}
