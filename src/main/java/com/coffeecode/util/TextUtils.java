package com.coffeecode.util;

public class TextUtils {

    private TextUtils() {
        throw new IllegalStateException("Utility class");
    
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
