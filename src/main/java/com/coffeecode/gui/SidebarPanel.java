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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
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
    private final JTextArea errorInputInfo;

    public SidebarPanel(int width) {
        // Initialize components
        sourceLanguageSelector = new JComboBox<>(Language.values());
        targetLanguageSelector = new JComboBox<>(Language.values());
        searchTypeSelector = new JComboBox<>(SearchType.values());
        trackingCheckbox = new JCheckBox("Enable Step Tracking");
        wordInput = createTextField();
        resultField = createTextField();
        resultField.setEditable(false);
        indexField = createTextField();
        indexField.setEditable(false);
        searchButton = createButton("Search", width);
        clearButton = createButton("Clear", width);
        errorInputInfo = createErrorInputField(width);

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
        addLabeledComponent("Error Info", errorInputInfo, width);
        addLabeledComponent("Target Language", targetLanguageSelector, width);

        // Search Configuration
        addLabeledComponent("Search Algorithm", searchTypeSelector, width);
        add(trackingCheckbox);
        add(searchButton);

        // Results
        addLabeledComponent("Translation", resultField, width);
        addLabeledComponent("Found at Index", indexField, width);

        // Control Buttons
        addVerticalSpace(VERTICAL_GAP);
        add(clearButton);
    }

    // Component Creation Methods
    private void addLabeledComponent(String labelText, JComponent component, int width) {
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(width, COMPONENT_HEIGHT));
        component.setPreferredSize(new Dimension(width, COMPONENT_HEIGHT));
        label.setAlignmentX(LEFT_ALIGNMENT);
        component.setAlignmentX(LEFT_ALIGNMENT);
        add(label);
        add(component);
    }

    private JTextArea createErrorInputField(int width) {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBackground(UIManager.getColor("TextField.background"));
        area.setBorder(BorderFactory.createEtchedBorder());
        area.setPreferredSize(new Dimension(width, COMPONENT_HEIGHT * 3)); // Increased height
        area.setVisible(true);
        area.setText(" "); // Initialize with space
        return area;
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
        button.setAlignmentX(LEFT_ALIGNMENT);
        return button;
    }

    // Configuration Methods
    private void configureComponents() {
        // Configure word input validation
        wordInput.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validateAllInputs();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateAllInputs();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateAllInputs();
            }
        });

        // Add language combo box listeners
        sourceLanguageSelector.addActionListener(e -> validateAllInputs());
        targetLanguageSelector.addActionListener(e -> validateAllInputs());
    }

    private void validateAllInputs() {
        StringBuilder errorMessage = new StringBuilder();

        // Validate word input
        String text = wordInput.getText();
        if (text == null || text.isBlank()) {
            errorMessage.append("Word cannot be empty");
        } else if (!text.matches("[a-zA-Z\\s-]+")) {
            errorMessage.append("Word can only contain letters, spaces and hyphens");
        }

        // Validate language selections
        Language source = getSourceLanguage();
        Language target = getTargetLanguage();
        if (source != null && target != null && source == target) {
            if (errorMessage.length() > 0) {
                errorMessage.append("\n");
            }
            errorMessage.append("Source and target languages must be different");
        }

        // Always show the error info field, just update its text
        if (errorMessage.length() > 0) {
            showError(errorMessage.toString());
        } else {
            // Don't hide the field, just clear the text
            errorInputInfo.setText(" "); // Space to maintain height
            errorInputInfo.setForeground(Color.BLACK);
        }
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
        errorInputInfo.setText(message);
        errorInputInfo.setForeground(Color.RED);
        errorInputInfo.setVisible(true); // Always visible
    }

    public void hideError() {
        errorInputInfo.setText(" "); // Space to maintain height
        errorInputInfo.setForeground(Color.BLACK);
        // Don't hide the component
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
