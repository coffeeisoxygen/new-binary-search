package com.coffeecode.model.result;

public class SearchStatistics {

    private long totalSearchTime;
    private int totalSteps;
    private int searchCount;

    public void recordSearch(long searchTime, int steps) {
        totalSearchTime += searchTime;
        totalSteps += steps;
        searchCount++;
    }

    public double getAverageSteps() {
        return searchCount == 0 ? 0 : (double) totalSteps / searchCount;
    }

    public double getAverageTime() {
        return searchCount == 0 ? 0 : (double) totalSearchTime / searchCount;
    }
}
