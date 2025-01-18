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
import com.coffeecode.validation.ValidationException;
import com.coffeecode.viewmodel.SidebarViewModel;

public class SidebarPanel extends JPanel {

    private final SidebarViewModel viewModel;

    private final JComboBox<Language> sourceLanguageSelector;
    private final JComboBox<Language> targetLanguageSelector;
    private final JComboBox<SearchType> searchTypeSelector;
    private final JCheckBox trackingCheckbox;
    private final JTextField wordInput;
    private final JTextField resultField;
    private final JTextField indexField;
    private final JButton searchButton;
    private final JButton clearButton;
    private final JButton openDictionaryButton;
    private final JLabel errorLabel;  // Add this field

    public SidebarPanel(int width, SidebarViewModel viewModel) {
        this.viewModel = viewModel;

        // Initialize all components first
        this.sourceLanguageSelector = new JComboBox<>(Language.values());
        this.targetLanguageSelector = new JComboBox<>(Language.values());
        this.searchTypeSelector = new JComboBox<>(SearchType.values());
        this.trackingCheckbox = new JCheckBox("Enable Step Tracking");
        this.wordInput = createTextField();
        this.resultField = createTextField();
        this.indexField = createTextField();
        this.searchButton = createButton("Search", width, 35);
        this.clearButton = createButton("Clear", width, 35);
        this.openDictionaryButton = createButton("Open Dictionary", width, 35);
        this.errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);

        // Setup panel properties
        setPreferredSize(new Dimension(width, 0));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Setup components
        setupComponents(width);
        setupValidation();
        setupListeners();
        setupBindings();
    }

    private void setupComponents(int width) {
        // Source Language
        add(createLabel("Source Language"));
        setComponentSize(sourceLanguageSelector, width, 25);
        add(sourceLanguageSelector);
        addVerticalSpace(10);

        // Input word
        add(createLabel("Word to Translate"));
        setComponentSize(wordInput, width, 25);
        add(wordInput);
        add(errorLabel);
        setComponentSize(errorLabel, width, 25);
        addVerticalSpace(5);
        addVerticalSpace(10);

        // Target Language
        add(createLabel("Target Language"));
        setComponentSize(targetLanguageSelector, width, 25);
        add(targetLanguageSelector);
        addVerticalSpace(10);

        // Search type selector
        add(createLabel("Search Algorithm"));
        setComponentSize(searchTypeSelector, width, 25);
        add(searchTypeSelector);
        addVerticalSpace(10);

        // Tracking checkbox
        add(trackingCheckbox);
        addVerticalSpace(10);

        // Search button
        add(searchButton);
        addVerticalSpace(10);

        // Result
        add(createLabel("Translation"));
        resultField.setEnabled(false);
        add(resultField);
        addVerticalSpace(10);

        // Index
        add(createLabel("Found at Index"));
        indexField.setEnabled(false);
        add(indexField);
        addVerticalSpace(10);

        // Clear button
        add(clearButton);
        addVerticalSpace(10);

        // Open Dictionary button
        add(openDictionaryButton);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(LEFT_ALIGNMENT);
        return label;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        setComponentSize(field, 250, 25);
        return field;
    }

    private JButton createButton(String text, int width, int height) {
        JButton button = new JButton(text);
        setComponentSize(button, width, height);
        return button;
    }

    private void setComponentSize(JComponent component, int width, int height) {
        component.setMaximumSize(new Dimension(width, height));
        component.setPreferredSize(new Dimension(width, height));
        component.setMinimumSize(new Dimension(width, height));
        component.setAlignmentX(LEFT_ALIGNMENT);
    }

    private void addVerticalSpace(int height) {
        add(Box.createRigidArea(new Dimension(0, height)));
    }

    // Getters
    public Language getSourceLanguage() {
        return (Language) sourceLanguageSelector.getSelectedItem();
    }

    public Language getTargetLanguage() {
        return (Language) targetLanguageSelector.getSelectedItem();
    }

    public SearchType getSelectedSearchType() {
        return (SearchType) searchTypeSelector.getSelectedItem();
    }

    public boolean isTrackingEnabled() {
        return trackingCheckbox.isSelected();
    }

    private void setupListeners() {
        searchButton.addActionListener(e -> {
            try {
                String word = wordInput.getText();
                Language source = getSourceLanguage();
                Language target = getTargetLanguage();
                
                // Final validation before search
                validateAll();
                
                // If validation passes, perform search
                viewModel.search(word, source, target);
            } catch (ValidationException ex) {
                showError(ex.getMessage());
            }
        });

        clearButton.addActionListener(e -> {
            wordInput.setText("");
            resultField.setText("");
            indexField.setText("");
        });

        searchTypeSelector.addActionListener(e -> {
            SearchType type = getSelectedSearchType();
            viewModel.setSearchStrategy(type, trackingCheckbox.isSelected());
        });

        // Add change listeners to prevent same language selection
        sourceLanguageSelector.addActionListener(e -> validateLanguageSelection());
        targetLanguageSelector.addActionListener(e -> validateLanguageSelection());
    }

    private void validateLanguageSelection() {
        Language source = getSourceLanguage();
        Language target = getTargetLanguage();
        
        searchButton.setEnabled(source != target);
        if (source == target) {
            showError("Source and target languages must be different");
        }
    }

    private void setupBindings() {
        viewModel.addPropertyChangeListener(evt -> {
            switch (evt.getPropertyName()) {
                case "translatedText" ->
                    resultField.setText((String) evt.getNewValue());
                case "foundIndex" ->
                    indexField.setText((String) evt.getNewValue());
                case "searching" -> {
                    boolean searching = (boolean) evt.getNewValue();
                    searchButton.setEnabled(!searching);
                    wordInput.setEnabled(!searching);
                }
                default -> {
                    // Handle unexpected property changes if necessary
                }
            }
        });
    }

    private void setupValidation() {
        // Validate on language selection change
        sourceLanguageSelector.addActionListener(e -> validateAll());
        targetLanguageSelector.addActionListener(e -> validateAll());
        
        // Validate on word input change
        wordInput.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { validateAll(); }
            public void removeUpdate(DocumentEvent e) { validateAll(); }
            public void insertUpdate(DocumentEvent e) { validateAll(); }
        });
    }

    private void validateAll() {
        try {
            // Clear previous error
            hideError();
            
            // Get current values
            String word = wordInput.getText();
            Language source = getSourceLanguage();
            Language target = getTargetLanguage();

            // Validate languages
            validateLanguages(source, target);
            
            // Validate word
            validateWord(word);
            
            // Enable search if all valid
            searchButton.setEnabled(true);
            
        } catch (ValidationException ex) {
            showError(ex.getMessage());
            searchButton.setEnabled(false);
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
        if (word.length() > 100) {
            throw new ValidationException("Word is too long (max 100 characters)");
        }
        if (!word.matches("[a-zA-Z\\s-]+")) {
            throw new ValidationException("Word can only contain letters, spaces and hyphens");
        }
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    private void hideError() {
        errorLabel.setText("");
        errorLabel.setVisible(false);
    }
}
