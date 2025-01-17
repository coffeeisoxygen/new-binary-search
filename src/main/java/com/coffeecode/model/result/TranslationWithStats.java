package com.coffeecode.model.result;

import java.util.List;

public record TranslationWithStats(
    boolean found,
    String translation,
    List<SearchStep> searchSteps,
    SearchStatistics statistics
) {}