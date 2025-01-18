package com.coffeecode.gui.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.gui.SidebarPanel;
import com.coffeecode.model.core.Language;
import com.coffeecode.model.search.SearchType;
import com.coffeecode.validation.ValidationException;
import com.coffeecode.viewmodel.SidebarViewModel;

public class SidebarHandler {

    private static final Logger logger = LoggerFactory.getLogger(SidebarHandler.class);

    private final SidebarPanel panel;
    private final SidebarViewModel viewModel;

    public SidebarHandler(SidebarPanel panel, SidebarViewModel viewModel) {
        this.panel = panel;
        this.viewModel = viewModel;

        initializeHandler();
    }

    private void initializeHandler() {
        setupInitialState();
        setupValidation();
        setupListeners();
        setupBindings();
        validateAll(); // Initial validation
    }

    private void setupInitialState() {
        panel.setSourceLanguage(Language.ENGLISH);
        panel.setTargetLanguage(Language.INDONESIAN);
        panel.setTracking(true);
        panel.setSearchType(SearchType.BINARY);
        viewModel.setSearchStrategy(SearchType.BINARY, true);
    }

    private void setupValidation() {
        panel.addWordValidationListener(this::validateAll);
        panel.addLanguageValidationListener(this::validateAll);
        panel.addSearchTypeListener(this::updateSearchStrategy);
    }

    private void setupListeners() {
        panel.addSearchListener(this::performSearch);
        panel.addClearListener(this::clearFields);
        panel.addWordValidationListener(this::validateAll);
        panel.addLanguageValidationListener(this::validateAll);
    }

    private void setupBindings() {
        viewModel.addPropertyChangeListener(evt -> {
            switch (evt.getPropertyName()) {
                case "translatedText" ->
                    panel.setResult((String) evt.getNewValue());
                case "foundIndex" ->
                    panel.setIndex((String) evt.getNewValue());
                case "searching" ->
                    panel.setSearchEnabled(!(boolean) evt.getNewValue());
                default ->
                    logger.debug("Unhandled property change: {}", evt.getPropertyName());
            }
        });
    }

    private void validateAll() {
        try {
            validateWord(panel.getWordInput());
            validateLanguages(panel.getSourceLanguage(), panel.getTargetLanguage());
            panel.hideError();
            panel.setSearchEnabled(true);
        } catch (ValidationException ex) {
            panel.showError(ex.getMessage());
            panel.setSearchEnabled(false);
            logger.debug("Validation failed: {}", ex.getMessage());
        }
    }

    private void validateLanguages(Language source, Language target) {
        if (source == null || target == null) {
            throw new ValidationException("Please select both languages");
        }
        if (source == target) {
            throw new ValidationException("Source and target languages must be different");
        }
    }

    private void validateWord(String word) {
        if (word == null || word.isBlank()) {
            throw new ValidationException("Word cannot be empty");
        }
        if (!word.matches("[a-zA-Z\\s-]+")) {
            throw new ValidationException("Word can only contain letters, spaces and hyphens");
        }
    }

    private void performSearch() {
        try {
            validateAll();
            viewModel.search(
                    panel.getWordInput(),
                    panel.getSourceLanguage(),
                    panel.getTargetLanguage()
            );
        } catch (ValidationException ex) {
            panel.showError(ex.getMessage());
            logger.debug("Search failed: {}", ex.getMessage());
        }
    }

    private void updateSearchStrategy() {
        viewModel.setSearchStrategy(
                panel.getSearchType(),
                panel.isTracking()
        );
        logger.debug("Search strategy updated: {} (tracked: {})",
                panel.getSearchType(), panel.isTracking());
    }

    private void clearFields() {
        panel.clearFields();
        panel.hideError();
        logger.debug("Fields cleared");
    }
}
