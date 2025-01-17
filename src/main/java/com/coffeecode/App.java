package com.coffeecode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.config.AppConfig;
import com.coffeecode.model.core.Language;
import com.coffeecode.model.search.SearchType;
import com.coffeecode.service.DictionaryService;

public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        try {
            AppConfig config = AppConfig.getDefault();
            DictionaryService service = new DictionaryService(config);

            // Demonstrate Binary Search with tracking
            demonstrateSearch(service, "apple", Language.ENGLISH, SearchType.BINARY, true);

            // Demonstrate Linear Search with tracking
            service.setSearchStrategy(SearchType.LINEAR, true);
            demonstrateSearch(service, "kucing", Language.INDONESIAN, SearchType.LINEAR, true);

            // Demonstrate without tracking
            service.setSearchStrategy(SearchType.BINARY, false);
            demonstrateSearch(service, "book", Language.ENGLISH, SearchType.BINARY, false);

        } catch (Exception e) {
            logger.error("Application error: {}", e.getMessage());
        }
    }

    private static void demonstrateSearch(DictionaryService service,
            String word,
            Language language,
            SearchType type,
            boolean tracking) {
        logger.info("Demonstrating {} search (tracked: {}) for: '{}' ({})",
                type, tracking, word, language);

        String result = service.translate(word, language);

        if (result != null) {
            logger.info("Translation found: {} -> {}", word, result);
            if (tracking) {
                service.getSearchSteps().forEach(step
                        -> logger.info("  {}", step.getStepDescription()));
            }
        } else {
            logger.warn("No translation found for: {}", word);
        }
        logger.info("-------------------");
    }
}
