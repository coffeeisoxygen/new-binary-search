package com.coffeecode.model.result;

import java.util.List;

public record TranslationResult(
        boolean found,
        String translation,
        List<SearchStep> searchSteps) {

}
