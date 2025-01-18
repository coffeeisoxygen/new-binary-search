package com.coffeecode.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.gui.handler.SidebarHandler;
import com.coffeecode.viewmodel.SidebarViewModel;
import com.coffeecode.viewmodel.VisualizationViewModel;

public class MainFrame extends JFrame {

    private static final Logger logger = LoggerFactory.getLogger(MainFrame.class);
    private final SidebarPanel sidebarPanel;
    private final StatisticsPanel statisticsPanel;
    private final VisualizationPanel visualizationPanel;
    private final SidebarHandler sidebarHandler;

    public MainFrame(SidebarViewModel sidebarViewModel, VisualizationViewModel visualizationViewModel) {
        // Frame setup
        setupFrame();

        // Initialize panels
        this.sidebarPanel = new SidebarPanel(250);
        this.visualizationPanel = new VisualizationPanel(visualizationViewModel);
        this.statisticsPanel = new StatisticsPanel();

        // Initialize handlers
        this.sidebarHandler = new SidebarHandler(sidebarPanel, sidebarViewModel);

        // Layout setup
        setupLayout();
    }

    private void setupFrame() {
        setTitle("Kamus & Visualisasi Search");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);
    }

    private void setupLayout() {
        // Left panel (Sidebar)
        add(sidebarPanel, BorderLayout.WEST);

        // Center panel (Visualization + Statistics)
        JPanel mainContentPanel = new JPanel(new BorderLayout(10, 10));
        mainContentPanel.add(visualizationPanel, BorderLayout.CENTER);
        mainContentPanel.add(statisticsPanel, BorderLayout.SOUTH);
        add(mainContentPanel, BorderLayout.CENTER);
    }

    public void initializeVisualization(int size) {
        visualizationPanel.initializeSize(size);
    }
}
