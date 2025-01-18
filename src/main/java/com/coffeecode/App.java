package com.coffeecode;

import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.config.AppConfig;
import com.coffeecode.gui.MainFrame;
import com.coffeecode.service.DictionaryService;
import com.coffeecode.viewmodel.SidebarViewModel;

public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        try {
            // Initialize core services
            AppConfig config = AppConfig.getDefault();
            DictionaryService dictionaryService = new DictionaryService(config);

            // Initialize view models
            SidebarViewModel sidebarViewModel = new SidebarViewModel(dictionaryService);

            // Launch UI
            SwingUtilities.invokeLater(() -> {
                try {
                    MainFrame mainFrame = new MainFrame(sidebarViewModel);
                    mainFrame.setVisible(true);
                    logger.info("Application UI initialized successfully");
                } catch (Exception e) {
                    logger.error("Failed to initialize UI: {}", e.getMessage());
                }
            });

        } catch (Exception e) {
            logger.error("Application failed to start: {}", e.getMessage());
        }
    }
}
