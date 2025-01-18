package com.coffeecode.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class VisualizationPanel extends JPanel {

    public VisualizationPanel() {
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
    }
}
