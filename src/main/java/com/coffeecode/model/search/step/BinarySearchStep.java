package com.coffeecode.model.search.step;

public class BinarySearchStep implements SearchStep {

    private final int low;
    private final int mid;
    private final int high;
    private final String currentWord;

    public BinarySearchStep(int low, int mid, int high, String currentWord) {
        this.low = low;
        this.mid = mid;
        this.high = high;
        this.currentWord = currentWord;
    }

    @Override
    public String getCurrentWord() {
        return currentWord;
    }

    @Override
    public String getStepDescription() {
        return String.format("Checking index %d (low=%d, high=%d) : %s",
                mid, low, high, currentWord);
    }
}
