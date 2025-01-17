package com.coffeecode.model.result;

public record SearchStep(
        int low,
        int mid,
        int high,
        String currentWord) {

}
