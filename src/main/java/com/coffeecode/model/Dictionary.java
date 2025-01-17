package com.coffeecode.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.coffeecode.util.TextUtils;

public class Dictionary {

    private final List<Vocabulary> englishToIndonesian;
    private final List<Vocabulary> indonesianToEnglish;

    public Dictionary(List<Vocabulary> vocabularies) {
        this.englishToIndonesian = new ArrayList<>(vocabularies);
        this.indonesianToEnglish = new ArrayList<>(vocabularies);
        initializeSortedLists();
    }

    private void initializeSortedLists() {
        Collections.sort(englishToIndonesian,
                (a, b) -> a.getWord(Language.ENGLISH).compareToIgnoreCase(b.getWord(Language.ENGLISH)));
        Collections.sort(indonesianToEnglish,
                (a, b) -> a.getWord(Language.INDONESIAN).compareToIgnoreCase(b.getWord(Language.INDONESIAN)));
    }

    public String translate(String word, Language sourceLanguage, List<SearchStep> searchSteps) {
        String sanitizedWord = TextUtils.sanitizeWord(word);
        if (sanitizedWord == null || sanitizedWord.isBlank()) {
            return null;
        }

        List<Vocabulary> searchList = sourceLanguage == Language.ENGLISH
                ? englishToIndonesian : indonesianToEnglish;

        return findTranslationWithSteps(sanitizedWord, sourceLanguage, sourceLanguage.opposite(),
                searchList, searchSteps);
    }

    private String findTranslationWithSteps(String word, Language sourceLanguage,
            Language targetLanguage, List<Vocabulary> searchList, List<SearchStep> steps) {
        int low = 0;
        int high = searchList.size() - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            steps.add(new SearchStep(low, mid, high, searchList.get(mid).getWord(sourceLanguage)));

            Vocabulary midVocab = searchList.get(mid);
            int comparison = midVocab.getWord(sourceLanguage).compareToIgnoreCase(word);

            if (comparison < 0) {
                low = mid + 1;
            } else if (comparison > 0) {
                high = mid - 1;
            } else {
                return midVocab.getWord(targetLanguage);
            }
        }
        return null;
    }

    public int size() {
        return englishToIndonesian.size();
    }
}
