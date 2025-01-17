package com.coffeecode.config;

public class AppConfig {

    // Default paths
    private static final String DEFAULT_SCHEMA_PATH = "src/main/resources/schema/vocabulary-schema.json";
    private static final String DEFAULT_VOCABULARY_PATH = "src/main/resources/vocabularies.json";
    private static final int DEFAULT_MAX_WORD_LENGTH = 100;

    private final String schemaPath;
    private final String vocabularyPath;
    private final int maxWordLength;

    private AppConfig(Builder builder) {
        this.schemaPath = builder.schemaPath;
        this.vocabularyPath = builder.vocabularyPath;
        this.maxWordLength = builder.maxWordLength;
    }

    public String getSchemaPath() {
        return schemaPath;
    }

    public String getVocabularyPath() {
        return vocabularyPath;
    }

    public int getMaxWordLength() {
        return maxWordLength;
    }

    public static class Builder {

        private String schemaPath = DEFAULT_SCHEMA_PATH;
        private String vocabularyPath = DEFAULT_VOCABULARY_PATH;
        private int maxWordLength = DEFAULT_MAX_WORD_LENGTH;

        public Builder schemaPath(String path) {
            this.schemaPath = path;
            return this;
        }

        public Builder vocabularyPath(String path) {
            this.vocabularyPath = path;
            return this;
        }

        public Builder maxWordLength(int length) {
            this.maxWordLength = length;
            return this;
        }

        public AppConfig build() {
            validate();
            return new AppConfig(this);
        }

        private void validate() {
            if (schemaPath == null || schemaPath.isBlank()) {
                throw new IllegalArgumentException("Schema path cannot be null or empty");
            }
            if (vocabularyPath == null || vocabularyPath.isBlank()) {
                throw new IllegalArgumentException("Vocabulary path cannot be null or empty");
            }
            if (maxWordLength <= 0) {
                throw new IllegalArgumentException("Max word length must be positive");
            }
        }
    }

    public static AppConfig getDefault() {
        return new Builder().build();
    }
}
