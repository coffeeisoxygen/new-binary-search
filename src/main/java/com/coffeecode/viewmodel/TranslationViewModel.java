package com.coffeecode.viewmodel;

import java.util.concurrent.CompletableFuture;
import com.coffeecode.model.core.Language;
import com.coffeecode.model.search.step.SearchStep;
import com.coffeecode.service.DictionaryService;
import java.util.List;

public class TranslationViewModel {

    private final DictionaryService service;

    public TranslationViewModel(DictionaryService service) {
        this.service = service;
    }

    public CompletableFuture<String> translate(String word, Language language) {
        return CompletableFuture.supplyAsync(() -> {
            if (word == null || word.trim().isEmpty()) {
                return "";
            }
            return service.translate(word.trim(), language);
        });
    }

    public List<String> getSearchSteps() {
        return service.getSearchSteps().stream()
                .map(SearchStep::getStepDescription)
                .toList();
    }
}
