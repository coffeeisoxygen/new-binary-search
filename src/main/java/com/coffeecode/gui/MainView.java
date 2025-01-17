package com.coffeecode.gui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import com.coffeecode.model.core.Language;
import com.coffeecode.service.DictionaryService;
import com.coffeecode.viewmodel.TranslationViewModel;
import java.awt.*;

public class MainView extends JFrame {

    private final JTextField inputField;
    private final JComboBox<Language> languageSelector;
    private final JTextArea resultArea;
    private final JTextArea stepsArea;
    private final TranslationViewModel viewModel;

    public MainView(DictionaryService service) {
        super("Dictionary Translator");
        this.viewModel = new TranslationViewModel(service);

        // Setup components
        inputField = new JTextField(20);
        languageSelector = new JComboBox<>(Language.values());
        resultArea = new JTextArea(3, 20);
        stepsArea = new JTextArea(10, 40);

        setupLayout();
        setupListeners();
        setupWindow();
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));

        // Input panel
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Word:"));
        inputPanel.add(inputField);
        inputPanel.add(new JLabel("Language:"));
        inputPanel.add(languageSelector);
        add(inputPanel, BorderLayout.NORTH);

        // Result panel
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.add(new JLabel("Translation:"), BorderLayout.NORTH);
        resultPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);
        add(resultPanel, BorderLayout.CENTER);

        // Steps panel
        JPanel stepsPanel = new JPanel(new BorderLayout());
        stepsPanel.add(new JLabel("Search Steps:"), BorderLayout.NORTH);
        stepsPanel.add(new JScrollPane(stepsArea), BorderLayout.CENTER);
        add(stepsPanel, BorderLayout.SOUTH);
    }

    private void setupListeners() {
        inputField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                updateTranslation();
            }

            public void removeUpdate(DocumentEvent e) {
                updateTranslation();
            }

            public void changedUpdate(DocumentEvent e) {
                updateTranslation();
            }
        });

        languageSelector.addActionListener(e -> updateTranslation());
    }

    private void updateTranslation() {
        String word = inputField.getText();
        Language language = (Language) languageSelector.getSelectedItem();

        viewModel.translate(word, language)
                .thenAccept(result -> {
                    resultArea.setText(result);
                    stepsArea.setText(String.join("\n", viewModel.getSearchSteps()));
                });
    }

    private void setupWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }
}
