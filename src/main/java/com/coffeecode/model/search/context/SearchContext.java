package com.coffeecode.model.search.context;

import java.util.Collections;
import java.util.List;
import com.coffeecode.model.core.Language;
import com.coffeecode.model.core.Vocabulary;
import com.coffeecode.model.search.BinarySearchStrategy;
import com.coffeecode.model.search.SearchStrategy;
import com.coffeecode.model.search.SearchStrategyFactory;
import com.coffeecode.model.search.SearchType;
import com.coffeecode.model.search.step.SearchStep;

public class SearchContext {

    private SearchStrategy searchStrategy;
    private final SearchTracker tracker;
    private boolean isTracking;

    public SearchContext() {
        this.searchStrategy = new BinarySearchStrategy();
        this.tracker = new SearchTracker();
        this.isTracking = true;
    }

    public void setStrategy(SearchType type) {
        this.searchStrategy = SearchStrategyFactory.createStrategy(type);
    }

    public void setTracking(boolean enabled) {
        this.isTracking = enabled;
    }

    public String search(List<Vocabulary> list, String word,
            Language sourceLanguage, Language targetLanguage) {
        if (isTracking) {
            tracker.clear();
        }
        return searchStrategy.findTranslation(list, word, sourceLanguage,
                targetLanguage, isTracking ? tracker : null);
    }

    public List<SearchStep> getSteps() {
        return isTracking ? tracker.getSteps() : Collections.emptyList();
    }
}
