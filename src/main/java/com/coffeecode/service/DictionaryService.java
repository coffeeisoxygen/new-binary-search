package com.coffeecode.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.config.AppConfig;
import com.coffeecode.model.core.Dictionary;
import com.coffeecode.model.core.Language;
import com.coffeecode.model.core.Vocabulary;


import com.coffeecode.model.json.service.IJsonService;
import com.coffeecode.model.json.service.JsonServicesFactory;
import com.coffeecode.model.search.BinarySearchStrategy;
import com.coffeecode.model.search.SearchStrategy;

import com.coffeecode.validation.WordValidator;

public class DictionaryService {

    private final Logger logger = LoggerFactory.getLogger(DictionaryService.class);
    private final Dictionary dictionary;
    private final SearchStrategy searchStrategy;
    private final IJsonService jsonService;

    public DictionaryService(AppConfig config) {
        this.jsonService = JsonServicesFactory.create(config);
        this.searchStrategy = new BinarySearchStrategy();
        this.dictionary = initializeDictionary();
    }

    public String translate(String word, Language sourceLanguage) {
        try {
            WordValidator.validateWord(word, sourceLanguage.name());
            String sanitizedWord = WordValidator.sanitizeWord(word);
            List<Vocabulary> searchList = dictionary.getSearchList(sourceLanguage);

            return searchStrategy.findTranslation(
                    searchList,
                    sanitizedWord,
                    sourceLanguage,
                    sourceLanguage.opposite()
            );
        } catch (Exception e) {
            logger.error("Translation failed: {}", e.getMessage());
            throw new DictionaryServiceException("Translation failed", e);
        }
    }

    private Dictionary initializeDictionary() {
        try {
            List<Vocabulary> vocabularies = jsonService.parseFile(null);
            logger.info("Dictionary initialized with {} words", vocabularies.size());
            return new Dictionary(vocabularies);
        } catch (Exception e) {
            logger.error("Dictionary initialization failed: {}", e.getMessage());
            throw new DictionaryServiceException("Failed to initialize dictionary", e);
        }
    }
}
