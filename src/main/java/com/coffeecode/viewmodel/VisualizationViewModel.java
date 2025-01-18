package com.coffeecode.viewmodel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class VisualizationViewModel {

    private final PropertyChangeSupport changes = new PropertyChangeSupport(this);
    private String result = "";
    private String index = "";

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        String oldValue = this.result;
        this.result = result;
        changes.firePropertyChange("result", oldValue, result);
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        String oldValue = this.index;
        this.index = index;
        changes.firePropertyChange("index", oldValue, index);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changes.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changes.removePropertyChangeListener(listener);
    }
}
