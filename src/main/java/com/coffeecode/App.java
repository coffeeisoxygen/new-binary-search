package com.coffeecode;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.model.Language;
import com.coffeecode.model.SearchStep;
import com.coffeecode.model.TranslationResult;
import com.coffeecode.viewmodel.DictionaryViewModel;

public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);
    private static final int MAX_WORD_LENGTH = 100;

    public static void main(String[] args) {
        try {
            DictionaryViewModel viewModel = new DictionaryViewModel();

            // Test various input scenarios
            testWithValidation(viewModel, "apple", Language.ENGLISH);
            testWithValidation(viewModel, "kucing", Language.INDONESIAN); // Special chars
            testWithValidation(viewModel, "", Language.ENGLISH);           // Empty
            testWithValidation(viewModel, null, Language.INDONESIAN);      // Null
            testWithValidation(viewModel, "a".repeat(150), Language.ENGLISH); // Too long

        } catch (Exception e) {
            logger.error("Application error: {}", e.getMessage());
        }
    }

    private static void testWithValidation(DictionaryViewModel viewModel, String word, Language language) {
        try {
            logger.info("Testing input: '{}'", word);

            // Validate input
            if (!isValidInput(word)) {
                logger.warn("Invalid input detected: {}", getValidationError(word));
                return;
            }

            // Proceed with translation
            viewModel.translate(word, language);
            logTranslationResult(viewModel.getLastResult(), word);

        } catch (Exception e) {
            logger.error("Error processing '{}': {}", word, e.getMessage());
        }
    }

    private static boolean isValidInput(String word) {
        return word != null
                && !word.isBlank()
                && word.length() <= MAX_WORD_LENGTH
                && word.matches("[a-zA-Z\\s-]+");
    }

    private static String getValidationError(String word) {
        if (word == null) {
            return "Input cannot be null";
        }
        if (word.isBlank()) {
            return "Input cannot be empty";
        }
        if (word.length() > MAX_WORD_LENGTH) {
            return "Input too long";
        }
        if (!word.matches("[a-zA-Z\\s-]+")) {
            return "Invalid characters";
        }
        return "Unknown validation error";
    }

    private static void logTranslationResult(TranslationResult result, String word) {
        if (result != null && result.found()) {
            logger.info("Translation found: {} -> {}", word, result.translation());
            logSearchSteps(result.searchSteps());
        } else {
            logger.warn("No translation found for: {}", word);
        }
        logger.info("-------------------");
    }

    private static void logSearchSteps(List<SearchStep> steps) {
        logger.info("Search steps:");
        steps.forEach(step
                -> logger.info("Step: low={}, mid={}, high={}, current={}",
                        step.low(), step.mid(), step.high(), step.currentWord())
        );
    }
}
