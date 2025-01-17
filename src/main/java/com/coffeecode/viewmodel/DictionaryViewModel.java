package com.coffeecode.viewmodel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.model.Language;
import com.coffeecode.model.TranslationResult;
import com.coffeecode.service.DictionaryService;

public class DictionaryViewModel {

    private static final Logger logger = LoggerFactory.getLogger(DictionaryViewModel.class);
    private final DictionaryService dictionaryService;

    public DictionaryViewModel() {
        this.dictionaryService = new DictionaryService();
    }

    public TranslationResult translate(String word, Language language) {
        try {
            TranslationResult result = dictionaryService.translate(word, language);
            if (result.found()) {
                logger.info("Translation found: {} -> {}", word, result.translation());
            } else {
                logger.warn("No translation found for: {}", word);
            }
            return result;
        } catch (Exception e) {
            logger.error("Translation error: {}", e.getMessage());
            return new TranslationResult(false, null);
        }
    }
}
