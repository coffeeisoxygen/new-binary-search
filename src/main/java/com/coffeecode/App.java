package com.coffeecode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.model.Language;
import com.coffeecode.viewmodel.DictionaryViewModel;

public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        try {
            DictionaryViewModel viewModel = new DictionaryViewModel();
            viewModel.translate("apple", Language.ENGLISH);
            viewModel.translate("kucing", Language.INDONESIAN);
        } catch (Exception e) {
            logger.error("Application error: {}", e.getMessage());
        }
    }
}
