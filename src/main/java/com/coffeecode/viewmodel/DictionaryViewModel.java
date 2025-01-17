package com.coffeecode.viewmodel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.model.Language;
import com.coffeecode.model.SearchStep;
import com.coffeecode.model.TranslationResult;
import com.coffeecode.service.DictionaryService;
import com.fasterxml.jackson.core.JsonParseException;

public class DictionaryViewModel {

    private static final Logger logger = LoggerFactory.getLogger(DictionaryViewModel.class);
    private final DictionaryService dictionaryService;
    private final PropertyChangeSupport propertyChange;
    private TranslationResult lastResult;
    private String errorMessage;
    private boolean isLoading;

    public DictionaryViewModel() throws JsonParseException {
        this.dictionaryService = new DictionaryService();
        this.propertyChange = new PropertyChangeSupport(this);
        this.isLoading = false;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChange.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChange.removePropertyChangeListener(listener);
    }

    public void translate(String word, Language language) {
        try {
            setLoading(true);
            setError(null);

            TranslationResult result = dictionaryService.translate(word, language);
            setTranslationResult(result);

            if (!result.found()) {
                setError("No translation found for: " + word);
            }
        } catch (Exception e) {
            logger.error("Translation error: {}", e.getMessage());
            setError(e.getMessage());
        } finally {
            setLoading(false);
        }
    }

    private void setTranslationResult(TranslationResult result) {
        TranslationResult oldResult = this.lastResult;
        this.lastResult = result;
        propertyChange.firePropertyChange("translationResult", oldResult, result);
    }

    private void setError(String message) {
        String oldError = this.errorMessage;
        this.errorMessage = message;
        propertyChange.firePropertyChange("error", oldError, message);
    }

    private void setLoading(boolean loading) {
        boolean oldLoading = this.isLoading;
        this.isLoading = loading;
        propertyChange.firePropertyChange("loading", oldLoading, loading);
    }

    public List<SearchStep> getSearchSteps() {
        return dictionaryService.getSearchSteps();
    }

    public int getDictionarySize() {
        return dictionaryService.getDictionarySize();
    }

    public TranslationResult getLastResult() {
        return lastResult;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isLoading() {
        return isLoading;
    }
}
