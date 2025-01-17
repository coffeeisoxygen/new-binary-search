package com.coffeecode;

import javax.swing.SwingUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.config.AppConfig;
import com.coffeecode.gui.MainView;

import com.coffeecode.service.DictionaryService;

public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        try {
            AppConfig config = AppConfig.getDefault();
            DictionaryService service = new DictionaryService(config);

            // Create and show GUI
            SwingUtilities.invokeLater(() -> {
                MainView view = new MainView(service);
                view.setVisible(true);
            });

        } catch (Exception e) {
            logger.error("Application error: {}", e.getMessage());
        }
    }
}
