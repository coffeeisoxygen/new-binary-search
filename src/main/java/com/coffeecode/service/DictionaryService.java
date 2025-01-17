package com.coffeecode.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.model.Dictionary;
import com.coffeecode.model.Language;
import com.coffeecode.model.TranslationResult;
import com.coffeecode.model.json.JsonParser;
import com.coffeecode.model.json.JsonServices;

public class DictionaryService {

    private static final Logger logger = LoggerFactory.getLogger(DictionaryService.class);
    private final Dictionary dictionary;

    public DictionaryService() {
        JsonServices jsonServices = new JsonServices();
        JsonParser jsonParser = new JsonParser(jsonServices);
        try {
            dictionary = new Dictionary(jsonParser.parseFile("src/main/resources/vocabularies.json"));
            logger.info("Dictionary initialized with {} words", dictionary.size());
        } catch (Exception e) {
            logger.error("Failed to initialize dictionary: {}", e.getMessage());
            throw new RuntimeException("Failed to initialize dictionary", e);
        }
    }

    public TranslationResult translate(String word, Language language) {
        String translation = dictionary.translate(word, language);
        if (translation != null) {
            logger.info("{} '{}' -> {} '{}'",
                    language, word,
                    (language == Language.ENGLISH ? "Indonesian" : "English"),
                    translation);
            return new TranslationResult(true, translation);
        }
        logger.warn("No translation found for {} word: {}", language, word);
        return new TranslationResult(false, null);
    }

    public int getDictionarySize() {
        return dictionary.size();
    }
}
