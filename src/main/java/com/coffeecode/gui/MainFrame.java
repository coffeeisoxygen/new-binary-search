package com.coffeecode.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.coffeecode.viewmodel.SidebarViewModel;

public class MainFrame extends JFrame {

    private final SidebarPanel sidebarPanel;
    private final StatisticsPanel statisticsPanel;
    private final VisualizationPanel visualizationPanel;

    public MainFrame(SidebarViewModel sidebarViewModel) {
        setTitle("Kamus & Visualisasi Search");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Create panels
        this.sidebarPanel = new SidebarPanel(250, sidebarViewModel);
        this.visualizationPanel = new VisualizationPanel();
        this.statisticsPanel = new StatisticsPanel();

        // Layout setup
        add(sidebarPanel, BorderLayout.WEST);
        JPanel mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.add(visualizationPanel, BorderLayout.CENTER);
        mainContentPanel.add(statisticsPanel, BorderLayout.SOUTH);
        add(mainContentPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
    }
}
