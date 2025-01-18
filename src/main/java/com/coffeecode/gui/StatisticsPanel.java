package com.coffeecode.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class StatisticsPanel extends JPanel {

    public StatisticsPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Statistik"));

        // Placeholder for statistics (you can add graphs, charts, etc. here)
        JPanel statsPanel = new JPanel();
        statsPanel.setBackground(Color.WHITE); // Placeholder color
        statsPanel.setPreferredSize(new Dimension(400, 150));
        add(statsPanel, BorderLayout.CENTER);
    }
}
