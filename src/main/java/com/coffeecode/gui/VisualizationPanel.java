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

import com.coffeecode.viewmodel.VisualizationViewModel;

public class VisualizationPanel extends JPanel {

    private final VisualizationViewModel viewModel;
    private final JTextField resultField;
    private final JTextField indexField;

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
    }

    private JTextField createTextField() {
        JTextField field = new JTextField(10);
        field.setEditable(false);
        return field;
    }

    private void bindViewModel() {
        viewModel.addPropertyChangeListener(evt -> {
            switch (evt.getPropertyName()) {
                case "result" -> {
                    resultField.setText((String) evt.getNewValue());
                    resultField.setEnabled(!((String) evt.getNewValue()).isEmpty());
                }
                case "index" -> {
                    indexField.setText((String) evt.getNewValue());
                    indexField.setEnabled(!((String) evt.getNewValue()).isEmpty());
                }
            }
        });
    }
}
