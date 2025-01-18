package com.coffeecode.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.coffeecode.model.core.Language;
import com.coffeecode.model.search.SearchType;

public class SidebarPanel extends JPanel {

    // Constants
    private static final int COMPONENT_HEIGHT = 25;
    private static final int VERTICAL_GAP = 5;

    // UI Components - Language Selection
    private final JComboBox<Language> sourceLanguageSelector;
    private final JComboBox<Language> targetLanguageSelector;

    // UI Components - Search Configuration
    private final JComboBox<SearchType> searchTypeSelector;
    private final JCheckBox trackingCheckbox;

    // UI Components - Input/Output
    private final JTextField wordInput;
    private final JTextField resultField;
    private final JTextField indexField;

    // UI Components - Controls
    private final JButton searchButton;
    private final JButton clearButton;
    private final JLabel errorLabel;

    /**
     * Constructs a new SidebarPanel with the specified width
     */
    public SidebarPanel(int width) {
        // Initialize components
        sourceLanguageSelector = new JComboBox<>(Language.values());
        targetLanguageSelector = new JComboBox<>(Language.values());
        searchTypeSelector = new JComboBox<>(SearchType.values());
        trackingCheckbox = new JCheckBox("Enable Step Tracking");
        wordInput = createTextField();
        resultField = createTextField();
        indexField = createTextField();
        searchButton = createButton("Search", width);
        clearButton = createButton("Clear", width);
        errorLabel = createErrorLabel();

        setupLayout(width);
        configureComponents();
    }

    // Layout Setup Methods
    private void setupLayout(int width) {
        setPreferredSize(new Dimension(width, 0));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        addComponents(width);
    }

    private void addComponents(int width) {
        // Language Selection
        addLabeledComponent("Source Language", sourceLanguageSelector, width);
        addLabeledComponent("Word to Translate", wordInput, width);
        add(errorLabel);
        addLabeledComponent("Target Language", targetLanguageSelector, width);

        // Search Configuration
        addLabeledComponent("Search Algorithm", searchTypeSelector, width);
        add(trackingCheckbox);

        // Results
        addLabeledComponent("Translation", resultField, width);
        addLabeledComponent("Found at Index", indexField, width);

        // Control Buttons
        add(searchButton);
        addVerticalSpace(VERTICAL_GAP);
        add(clearButton);
    }

    // Component Creation Methods
    private void addLabeledComponent(String labelText, JComponent component, int width) {
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(width, COMPONENT_HEIGHT));
        component.setPreferredSize(new Dimension(width, COMPONENT_HEIGHT));
        add(label);
        add(component);
    }

    private JLabel createErrorLabel() {
        JLabel label = new JLabel();
        label.setForeground(Color.RED);
        label.setVisible(false);
        return label;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setEditable(true);
        return field;
    }

    private JButton createButton(String text, int width) {
        JButton button = new JButton(text);
        Dimension size = new Dimension(width, COMPONENT_HEIGHT);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setAlignmentX(CENTER_ALIGNMENT);
        return button;
    }

    // Configuration Methods
    private void configureComponents() {
        wordInput.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validateInput();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateInput();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateInput();
            }

            private void validateInput() {
                String text = wordInput.getText();
                if (text == null || text.isBlank()) {
                    showError("Word cannot be empty");
                } else if (!text.matches("[a-zA-Z\\s-]+")) {
                    showError("Word can only contain letters, spaces and hyphens");
                } else {
                    hideError();
                }
            }
        });
    }

    // Public API Methods - Getters and Setters
    public void setSourceLanguage(Language language) {
        sourceLanguageSelector.setSelectedItem(language);
    }

    public Language getSourceLanguage() {
        return (Language) sourceLanguageSelector.getSelectedItem();
    }

    public void setTargetLanguage(Language language) {
        targetLanguageSelector.setSelectedItem(language);
    }

    public Language getTargetLanguage() {
        return (Language) targetLanguageSelector.getSelectedItem();
    }

    public void setSearchType(SearchType type) {
        searchTypeSelector.setSelectedItem(type);
    }

    public SearchType getSearchType() {
        return (SearchType) searchTypeSelector.getSelectedItem();
    }

    public void setTracking(boolean enabled) {
        trackingCheckbox.setSelected(enabled);
    }

    public boolean isTracking() {
        return trackingCheckbox.isSelected();
    }

    public String getWordInput() {
        return wordInput.getText();
    }

    public void setWordInput(String text) {
        wordInput.setText(text);
    }

    public void setResult(String text) {
        resultField.setText(text);
    }

    public void setIndex(String index) {
        indexField.setText(index);
    }

    public void setSearchEnabled(boolean enabled) {
        searchButton.setEnabled(enabled);
    }

    public void clearFields() {
        wordInput.setText("");
        resultField.setText("");
        indexField.setText("");
        hideError();
    }

    // Public API Methods - UI State Management
    public void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    public void hideError() {
        errorLabel.setVisible(false);
    }

    // Event Registration Methods
    public void addSearchListener(Runnable action) {
        searchButton.addActionListener(e -> action.run());
    }

    public void addClearListener(Runnable action) {
        clearButton.addActionListener(e -> action.run());
    }

    public void addSearchTypeListener(Runnable action) {
        searchTypeSelector.addActionListener(e -> action.run());
    }

    public void addTrackingListener(Runnable action) {
        trackingCheckbox.addActionListener(e -> action.run());
    }

    public void addWordValidationListener(Runnable action) {
        wordInput.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                action.run();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                action.run();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                action.run();
            }
        });
    }

    public void addLanguageValidationListener(Runnable action) {
        sourceLanguageSelector.addActionListener(e -> action.run());
        targetLanguageSelector.addActionListener(e -> action.run());
    }

    // Helper Methods
    private void addVerticalSpace(int height) {
        add(Box.createRigidArea(new Dimension(0, height)));
    }
}
