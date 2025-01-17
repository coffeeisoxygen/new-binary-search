package com.coffeecode.model.search.step;

import com.coffeecode.model.search.SearchType;

public interface SearchStep {

    String getCurrentWord();

    String getStepDescription();
    SearchType getType();
}
