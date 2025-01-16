package com.coffeecode;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.model.Dictionary;
import com.coffeecode.model.json.JsonParser;
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

            // Demo English to Indonesian
            demonstrateEnglishToIndonesian(dictionary);

            // Demo Indonesian to English
            demonstrateIndonesianToEnglish(dictionary);

        } catch (Exception e) {
            logger.error("An error occurred: ", e);
        }
    }

    private static void demonstrateEnglishToIndonesian(Dictionary dictionary) {
        String[] englishWords = {"apple", "computer", "unknown"};
        for (String word : englishWords) {
            String translation = dictionary.findIndonesian(word);
            if (translation != null) {
                logger.info("English '{}' -> Indonesian '{}'", word, translation);
            } else {
                logger.warn("No translation found for '{}'", word);
            }
        }
    }

    private static void demonstrateIndonesianToEnglish(Dictionary dictionary) {
        String[] indonesianWords = {"apel", "komputer", "tidak ada"};
        for (String word : indonesianWords) {
            String translation = dictionary.findEnglish(word);
            if (translation != null) {
                logger.info("Indonesian '{}' -> English '{}'", word, translation);
            } else {
                logger.warn("No translation found for '{}'", word);
            }
        }
    }
}
