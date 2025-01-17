package com.coffeecode.model.search;

import java.util.List;
import com.coffeecode.model.core.Language;
import com.coffeecode.model.core.Vocabulary;

public class LinearSearchStrategy implements SearchStrategy {

    @Override
    public String findTranslation(List<Vocabulary> list,
            String word,
            Language sourceLanguage,
            Language targetLanguage) {
        for (Vocabulary vocab : list) {
            if (vocab.getWord(sourceLanguage).equalsIgnoreCase(word)) {
                return vocab.getWord(targetLanguage);
            }
        }
        return null;
    }

}
