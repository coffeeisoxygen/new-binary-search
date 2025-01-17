package com.coffeecode.model.search;

public class SearchStrategyFactory {

    private SearchStrategyFactory() {
        throw new IllegalStateException("Utility class");
    }

    public static SearchStrategy createStrategy(SearchType type, boolean tracked) {
        SearchStrategy baseStrategy = switch (type) {
            case BINARY ->
                new BinarySearchStrategy();
            case LINEAR ->
                new LinearSearchStrategy();
        };

        return tracked ? createTrackedStrategy(type, baseStrategy) : baseStrategy;
    }

    private static SearchStrategy createTrackedStrategy(SearchType type, SearchStrategy base) {
        return switch (type) {
            case BINARY ->
                new TrackedBinarySearch(base);
            case LINEAR ->
                new TrackedLinearSearch(base);
        };
    }
}
