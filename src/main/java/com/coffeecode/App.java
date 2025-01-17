package com.coffeecode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.config.AppConfig;
import com.coffeecode.model.core.Language;
import com.coffeecode.service.DictionaryService;

public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        try {
            AppConfig config = AppConfig.getDefault();
            DictionaryService service = new DictionaryService(config);

            // Test core translation
            testTranslation(service, "apple", Language.ENGLISH);
            testTranslation(service, "kucing", Language.INDONESIAN);

            // Test error cases
            testTranslation(service, "", Language.ENGLISH);
            testTranslation(service, null, Language.INDONESIAN);
            testTranslation(service, "nonexistent", Language.ENGLISH);

        } catch (Exception e) {
            logger.error("Application error: {}", e.getMessage());
        }
    }

    private static void testTranslation(DictionaryService service,
            String word,
            Language language) {
        try {
            logger.info("Testing translation: '{}' ({})", word, language);
            String result = service.translate(word, language);

            if (result != null) {
                logger.info("Translation found: {} -> {}", word, result);
            } else {
                logger.warn("No translation found for: {}", word);
            }
        } catch (Exception e) {
            logger.error("Translation error for '{}': {}", word, e.getMessage());
        }
        logger.info("-------------------");
    }
}
