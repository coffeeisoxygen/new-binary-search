package com.coffeecode.model.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.coffeecode.model.search.step.SearchStep;

public abstract class TrackedSearchStrategy implements SearchStrategy {

    protected final SearchStrategy searchStrategy;
    protected final List<SearchStep> steps;

    protected TrackedSearchStrategy(SearchStrategy searchStrategy) {
        this.searchStrategy = searchStrategy;
        this.steps = new ArrayList<>();
    }

    public List<SearchStep> getSteps() {
        return Collections.unmodifiableList(steps);
    }
}
