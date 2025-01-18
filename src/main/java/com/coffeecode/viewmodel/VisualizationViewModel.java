package com.coffeecode.viewmodel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import com.coffeecode.model.search.step.SearchStep;

public class VisualizationViewModel {
    private final PropertyChangeSupport propertyChangeSupport;
    private String result;
    private String index;
    private int size;
    private SearchStep currentStep;

    public VisualizationViewModel() {
        this.propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        String oldResult = this.result;
        this.result = result;
        propertyChangeSupport.firePropertyChange("result", oldResult, result);
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        String oldIndex = this.index;
        this.index = index;
        propertyChangeSupport.firePropertyChange("index", oldIndex, index);
    }

    public void setSize(int size) {
        int oldSize = this.size;
        this.size = size;
        propertyChangeSupport.firePropertyChange("size", oldSize, size);
    }

    public int getSize() {
        return size;
    }

    public void setCurrentStep(SearchStep step) {
        SearchStep oldStep = this.currentStep;
        this.currentStep = step;
        propertyChangeSupport.firePropertyChange("currentStep", oldStep, step);
    }

    public SearchStep getCurrentStep() {
        return currentStep;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}
