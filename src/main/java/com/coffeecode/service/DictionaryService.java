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
import com.coffeecode.model.search.SearchType;
import com.coffeecode.model.search.context.SearchContext;
import com.coffeecode.model.search.step.SearchStep;
import com.coffeecode.validation.ValidationException;
import com.coffeecode.validation.WordValidator;

public class DictionaryService {

    private final Logger logger = LoggerFactory.getLogger(DictionaryService.class);
    private final Dictionary dictionary;
    private final IJsonService jsonService;
    private final SearchContext searchContext;

    public DictionaryService(AppConfig config) {
        this.searchContext = new SearchContext();
        this.jsonService = JsonServicesFactory.create(config);
        this.dictionary = initializeDictionary();
    }

    public void setSearchStrategy(SearchType type, boolean tracked) {
        searchContext.setStrategy(type);
        searchContext.setTracking(tracked);
        logger.info("Search strategy changed to: {} (tracked: {})", type, tracked);
    }

    public List<SearchStep> getSearchSteps() {
        return searchContext.getSteps();
    }

    public String translate(String word, Language sourceLanguage, Language targetLanguage) {
        try {
            // Validate inputs
            WordValidator.validateWord(word, sourceLanguage.name());
            validateLanguages(sourceLanguage, targetLanguage);

            // Process translation
            String sanitizedWord = WordValidator.sanitizeWord(word);
            List<Vocabulary> searchList = dictionary.getSearchList(sourceLanguage);

            return searchContext.search(searchList, sanitizedWord,
                    sourceLanguage, targetLanguage);
        } catch (Exception e) {
            logger.error("Translation failed: {}", e.getMessage());
            throw new DictionaryServiceException("Translation failed", e);
        }
    }

    private void validateLanguages(Language source, Language target) {
        if (source == target) {
            throw new ValidationException("Source and target languages must be different");
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
