package com.coffeecode.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.model.Dictionary;
import com.coffeecode.model.Language;
import com.coffeecode.model.SearchStep;
import com.coffeecode.model.TranslationResult;
import com.coffeecode.model.json.JsonParser;
import com.coffeecode.model.json.JsonServices;
import com.fasterxml.jackson.core.JsonParseException;

public class DictionaryService {

    private static final Logger logger = LoggerFactory.getLogger(DictionaryService.class);
    private final Dictionary dictionary;
    private final List<SearchStep> searchSteps;

    public DictionaryService() throws JsonParseException {
        JsonServices jsonServices = new JsonServices();
        JsonParser jsonParser = new JsonParser(jsonServices);
        this.searchSteps = new ArrayList<>();
        try {
            dictionary = new Dictionary(jsonParser.parseFile("src/main/resources/vocabularies.json"));
            logger.info("Dictionary initialized with {} words", dictionary.size());
        } catch (Exception e) {
            logger.error("Unexpected error during dictionary initialization: {}", e.getMessage());
            throw new DictionaryServiceException("Unexpected error during dictionary initialization", e);
        }
    }

    public TranslationResult translate(String word, Language sourceLanguage) {
        searchSteps.clear();
        String translation = dictionary.translate(word, sourceLanguage, searchSteps);
        return new TranslationResult(translation != null, translation, searchSteps);
    }

    public int getDictionarySize() {
        return dictionary.size();
    }

    public List<SearchStep> getSearchSteps() {
        return searchSteps;
    }
}
