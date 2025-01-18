package com.coffeecode.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import com.coffeecode.gui.components.SearchVisualizerComponent;
import com.coffeecode.model.search.step.SearchStep;
import com.coffeecode.viewmodel.VisualizationViewModel;

public class VisualizationPanel extends JPanel {

    private final VisualizationViewModel viewModel;
    private final JTextField resultField;
    private final JTextField indexField;
    private final SearchVisualizerComponent visualizer;

    public VisualizationPanel(VisualizationViewModel viewModel) {
        this.viewModel = viewModel;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Visualisasi"));

        // Controls for visualization
        JPanel controlPanel = new JPanel();
        controlPanel.add(new JButton("Play"));
        controlPanel.add(new JButton("Pause"));
        controlPanel.add(new JButton("Stop"));
        controlPanel.add(new JLabel("Speed:"));
        controlPanel.add(new JSlider(1, 10, 5)); // Speed slider
        add(controlPanel, BorderLayout.NORTH);

        // Placeholder for visualization area
        JPanel visualPanel = new JPanel();
        visualPanel.setBackground(Color.LIGHT_GRAY); // Just a placeholder color
        visualPanel.setPreferredSize(new Dimension(400, 200));
        add(visualPanel, BorderLayout.CENTER);

        // Initialize visualizer
        visualizer = new SearchVisualizerComponent();
        visualPanel.add(visualizer);

        // Result and Index fields
        resultField = createTextField();
        indexField = createTextField();
        resultField.setEditable(false);
        indexField.setEditable(false);
        controlPanel.add(new JLabel("Result:"));
        controlPanel.add(resultField);
        controlPanel.add(new JLabel("Index:"));
        controlPanel.add(indexField);

        // Bind view model
        bindViewModel();

        // Update the visualizer when steps change
        viewModel.addPropertyChangeListener(evt -> {
            if ("currentStep".equals(evt.getPropertyName())) {
                visualizer.updateStep((SearchStep) evt.getNewValue());
            }
        });
    }

    public void initializeSize(int size) {
        visualizer.setDictionarySize(size); // Call visualizer directly
        viewModel.setSize(size);
    }

    private JTextField createTextField() {
        JTextField field = new JTextField(10);
        field.setEditable(false);
        return field;
    }

    private void bindViewModel() {
        viewModel.addPropertyChangeListener(evt -> {
            String propertyName = evt.getPropertyName();
            switch (propertyName) {
                case "result" -> {
                    resultField.setText((String) evt.getNewValue());
                    resultField.setEnabled(!((String) evt.getNewValue()).isEmpty());
                }
                case "index" -> {
                    indexField.setText((String) evt.getNewValue());
                    indexField.setEnabled(!((String) evt.getNewValue()).isEmpty());
                }
                case "size" -> visualizer.setDictionarySize((Integer) evt.getNewValue());
                case "currentStep" -> visualizer.updateStep((SearchStep) evt.getNewValue());
                default -> System.out.println("Unknown property: " + propertyName);
            }
        });
    }
}
