package com.coffeecode.viewmodel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.model.Language;
import com.coffeecode.model.SearchStep;
import com.coffeecode.model.TranslationResult;
import com.coffeecode.service.DictionaryService;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class DictionaryViewModel {

    private static final Logger logger = LoggerFactory.getLogger(DictionaryViewModel.class);
    private final DictionaryService dictionaryService;
    private PropertyChangeSupport propertyChange;

    public DictionaryViewModel() {
        this.dictionaryService = new DictionaryService();
        this.propertyChange = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChange.addPropertyChangeListener(listener);
    }

    public void translate(String word, Language language) {
        try {
            TranslationResult result = dictionaryService.translate(word, language);
            propertyChange.firePropertyChange("translationResult", null, result);
        } catch (Exception e) {
            logger.error("Translation error: {}", e.getMessage());
            propertyChange.firePropertyChange("error", null, e.getMessage());
        }
    }

    public List<SearchStep> getSearchSteps() {
        return dictionaryService.getSearchSteps();
    }
}
