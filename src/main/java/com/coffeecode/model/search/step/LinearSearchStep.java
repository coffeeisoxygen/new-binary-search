package com.coffeecode.model.search.step;

public class LinearSearchStep implements SearchStep {

    private final int index;
    private final String currentWord;

    public LinearSearchStep(int index, String currentWord) {
        this.index = index;
        this.currentWord = currentWord;
    }

    @Override
    public String getCurrentWord() {
        return currentWord;
    }

    @Override
    public String getStepDescription() {
        return String.format("Checking index %d : %s", index, currentWord);
    }
}
