package com.coffeecode.viewmodel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collections;
import java.util.List;

import com.coffeecode.model.core.Language;
import com.coffeecode.model.search.SearchType;
import com.coffeecode.model.search.step.SearchStep;
import com.coffeecode.service.DictionaryService;
import com.coffeecode.validation.ValidationException;

public class SidebarViewModel {

    private final PropertyChangeSupport changes = new PropertyChangeSupport(this);
    private final DictionaryService service;
    private String translatedText = "";
    private String foundIndex = "";
    private boolean isSearching = false;
    private List<SearchStep> currentSteps = Collections.emptyList();

    public SidebarViewModel(DictionaryService service) {
        this.service = service;
    }

    public void search(String word, Language sourceLanguage, Language targetLanguage) {
        setSearching(true);
        try {
            validateSearchParameters(word, sourceLanguage, targetLanguage);
            String result = service.translate(word, sourceLanguage, targetLanguage);
            setTranslatedText(result != null ? result : "Not found");
            setFoundIndex(getLastSearchIndex());
            setCurrentSteps(service.getSearchSteps());
        } catch (ValidationException e) {
            setTranslatedText("Error: " + e.getMessage());
        } finally {
            setSearching(false);
        }
    }

    private void validateSearchParameters(String word, Language source, Language target) {
        if (word == null || word.isBlank()) {
            throw new ValidationException("Word cannot be empty");
        }
        if (source == null || target == null) {
            throw new ValidationException("Languages must be selected");
        }
        if (source == target) {
            throw new ValidationException("Source and target languages must be different");
        }
        if (!word.matches("[a-zA-Z\\s-]+")) {
            throw new ValidationException("Word must contain only letters, spaces, and hyphens");
        }
    }

    public void setSearchStrategy(SearchType type, boolean tracked) {
        service.setSearchStrategy(type, tracked);
    }

    private String getLastSearchIndex() {
        var steps = service.getSearchSteps();
        return steps.isEmpty() ? "-"
                : String.valueOf(steps.get(steps.size() - 1).getCurrentIndex());
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changes.addPropertyChangeListener(listener);
    }

    private void setTranslatedText(String text) {
        String oldValue = translatedText;
        translatedText = text;
        changes.firePropertyChange("translatedText", oldValue, text);
    }

    private void setFoundIndex(String index) {
        String oldValue = foundIndex;
        foundIndex = index;
        changes.firePropertyChange("foundIndex", oldValue, index);
    }

    private void setSearching(boolean searching) {
        boolean oldValue = isSearching;
        isSearching = searching;
        changes.firePropertyChange("searching", oldValue, searching);
    }

    private void setCurrentSteps(List<SearchStep> steps) {
        List<SearchStep> oldValue = currentSteps;
        currentSteps = steps;
        changes.firePropertyChange("searchSteps", oldValue, steps);
    }

    public List<SearchStep> getCurrentSteps() {
        return Collections.unmodifiableList(currentSteps);
    }
}
