package com.coffeecode.model.search;

import java.util.List;
import com.coffeecode.model.core.Language;
import com.coffeecode.model.core.Vocabulary;



public class BinarySearchStrategy implements SearchStrategy {

    @Override
    public String findTranslation(List<Vocabulary> list,
            String word,
            Language sourceLanguage,
            Language targetLanguage) {
        int low = 0;
        int high = list.size() - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            Vocabulary midVocab = list.get(mid);
            int comparison = midVocab.getWord(sourceLanguage)
                    .compareToIgnoreCase(word);

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
