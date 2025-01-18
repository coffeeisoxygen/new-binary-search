package com.coffeecode.model.search.step;

import com.coffeecode.model.search.SearchType;


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
    public int getCurrentIndex() {
        return mid;
    }

    @Override
    public String getStepDescription() {
        return String.format("Checking index %d (mid=%d, low=%d, high=%d) : %s", 
            mid, mid, low, high, currentWord);
    }

    @Override
    public SearchType getType() {
        return SearchType.BINARY;
    }

    // Add these new getter methods
    public int getLow() {
        return low;
    }

    public int getHigh() {
        return high;
    }
}
