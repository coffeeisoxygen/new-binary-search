package com.coffeecode;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.model.Dictionary;
import com.coffeecode.model.Language;
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

            testSingleWordTranslation(dictionary, "cat&#@", Language.ENGLISH);     // English -> Indonesian
            testSingleWordTranslation(dictionary, "masakan", Language.INDONESIAN); // Indonesian -> English

        } catch (JsonParsingException e) {
            logger.error("Failed to parse JSON: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("An unexpected error occurred: ", e);
        }
    }

    private static void testSingleWordTranslation(Dictionary dictionary, String word, Language language) {
        String translation = dictionary.translate(word, language);
        if (translation != null) {
            logger.info("{} '{}' -> {} '{}'",
                    language, word,
                    (language == Language.ENGLISH ? "Indonesian" : "English"),
                    translation);
        } else {
            logger.warn("No translation found for {} word '{}'", language, word);
        }
    }
}
