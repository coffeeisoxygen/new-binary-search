package com.coffeecode.model.search;

public class SearchStrategyFactory {

    private SearchStrategyFactory() {
        throw new IllegalStateException("Utility class");
    }

    public static SearchStrategy createStrategy(SearchType type) {
        return switch (type) {
            case BINARY ->
                new BinarySearchStrategy();
            case LINEAR ->
                new LinearSearchStrategy();
        };
    }
}
