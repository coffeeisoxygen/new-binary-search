package com.coffeecode.model.search.context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.coffeecode.model.search.step.SearchStep;

public class SearchTracker {

    private final List<SearchStep> steps = new ArrayList<>();

    public void addStep(SearchStep step) {
        steps.add(step);
    }

    public void clear() {
        steps.clear();
    }

    public List<SearchStep> getSteps() {
        return Collections.unmodifiableList(steps);
    }
}
