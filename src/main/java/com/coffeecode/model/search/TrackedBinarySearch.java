package com.coffeecode.model.search;

import java.util.List;
import com.coffeecode.model.core.Language;
import com.coffeecode.model.core.Vocabulary;
import com.coffeecode.model.search.step.BinarySearchStep;

public class TrackedBinarySearch extends TrackedSearchStrategy {

    public TrackedBinarySearch(SearchStrategy searchStrategy) {
        super(searchStrategy);
    }

    @Override
    public String findTranslation(List<Vocabulary> list, String word,
            Language sourceLanguage, Language targetLanguage) {
        steps.clear();
        int low = 0;
        int high = list.size() - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            Vocabulary midVocab = list.get(mid);
            String currentWord = midVocab.getWord(sourceLanguage);

            steps.add(new BinarySearchStep(low, mid, high, currentWord));

            int comparison = currentWord.compareToIgnoreCase(word);
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
}
