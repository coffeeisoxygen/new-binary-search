package com.coffeecode;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.model.Dictionary;
import com.coffeecode.model.json.JsonParser;
import com.coffeecode.model.json.JsonParsingException;
import com.coffeecode.model.json.JsonServices;
import com.coffeecode.model.record.Vocabulary;

public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        try {
            // Initialize services
            JsonServices jsonServices = new JsonServices();
            JsonParser jsonParser = new JsonParser(jsonServices);

            // Load and parse vocabulary
            logger.info("Loading vocabulary from file...");
            List<Vocabulary> vocabularies = jsonParser.parseFile("src/main/resources/vocabularies.json");

            // Create dictionary
            Dictionary dictionary = new Dictionary(vocabularies);
            logger.info("Dictionary loaded with {} words", dictionary.size());

            // Test single word translations
            testSingleWordTranslation(dictionary, "apple", "apel");
            testSingleWordTranslation(dictionary, "computer", "komputer");
            testSingleWordTranslation(dictionary, "unknown", "tidak ada");

        } catch (JsonParsingException e) {
            logger.error("Failed to parse JSON: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("An unexpected error occurred: ", e);
        }
    }

    private static void testSingleWordTranslation(Dictionary dictionary, String englishWord, String indonesianWord) {
        try {
            String indonesianTranslation = dictionary.findIndonesian(englishWord);
            if (indonesianTranslation != null) {
                logger.info("English '{}' -> Indonesian '{}'", englishWord, indonesianTranslation);
            } else {
                logger.warn("No translation found for English word '{}'", englishWord);
            }

            String englishTranslation = dictionary.findEnglish(indonesianWord);
            if (englishTranslation != null) {
                logger.info("Indonesian '{}' -> English '{}'", indonesianWord, englishTranslation);
            } else {
                logger.warn("No translation found for Indonesian word '{}'", indonesianWord);
            }
        } catch (Exception e) {
            logger.error("Error finding translation for words '{}', '{}': {}", englishWord, indonesianWord, e.getMessage());
        }
    }
}
