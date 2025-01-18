package com.coffeecode;

import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.config.AppConfig;
import com.coffeecode.gui.MainFrame;
import com.coffeecode.model.search.step.SearchStep;
import com.coffeecode.service.DictionaryService;
import com.coffeecode.viewmodel.SidebarViewModel;
import com.coffeecode.viewmodel.VisualizationViewModel;

public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable)
                -> logger.error("Uncaught exception in thread {}: {}",
                        thread.getName(), throwable.getMessage()));

        try {
            initializeAndStartApplication();
        } catch (Exception e) {
            logger.error("Application failed to start: {}", e.getMessage(), e);
            System.exit(1);
        }
    }

    private static void initializeAndStartApplication() {
        // Initialize services
        AppConfig config = AppConfig.getDefault();
        DictionaryService dictionaryService = new DictionaryService(config);

        // Initialize view models
        VisualizationViewModel visualizationViewModel = new VisualizationViewModel();
        SidebarViewModel sidebarViewModel = new SidebarViewModel(dictionaryService);

        // Connect the view models
        sidebarViewModel.addPropertyChangeListener(evt -> {
            if ("searchSteps".equals(evt.getPropertyName())) {
                var steps = sidebarViewModel.getCurrentSteps();
                if (!steps.isEmpty()) {
                    // Update result and index
                    SearchStep lastStep = steps.get(steps.size() - 1);
                    visualizationViewModel.setResult(lastStep.getCurrentWord());
                    visualizationViewModel.setIndex(String.valueOf(lastStep.getCurrentIndex()));

                    // Update current step for visualization
                    visualizationViewModel.setCurrentStep(lastStep);
                }
            }
        });

        // Launch UI
        SwingUtilities.invokeLater(() -> {
            try {
                MainFrame mainFrame = new MainFrame(sidebarViewModel, visualizationViewModel);
                // Set initial dictionary size for visualizer
                mainFrame.initializeVisualization(dictionaryService.getDictionarySize());
                mainFrame.setVisible(true);
                logger.info("Application UI initialized successfully");
            } catch (Exception e) {
                logger.error("Failed to initialize UI: {}", e.getMessage(), e);
                System.exit(1);
            }
        });

        // Add shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Application shutting down...");
        }));
    }
}
