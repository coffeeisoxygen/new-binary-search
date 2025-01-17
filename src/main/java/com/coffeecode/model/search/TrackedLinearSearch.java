package com.coffeecode.model.search;

import java.util.List;
import com.coffeecode.model.core.Language;
import com.coffeecode.model.core.Vocabulary;
import com.coffeecode.model.search.step.LinearSearchStep;

public class TrackedLinearSearch extends TrackedSearchStrategy {

    public TrackedLinearSearch(SearchStrategy searchStrategy) {
        super(searchStrategy);
    }

    @Override
    public String findTranslation(List<Vocabulary> list, String word,
            Language sourceLanguage, Language targetLanguage) {
        steps.clear();
        for (int i = 0; i < list.size(); i++) {
            Vocabulary vocab = list.get(i);
            String currentWord = vocab.getWord(sourceLanguage);

            steps.add(new LinearSearchStep(i, currentWord));

            if (currentWord.equalsIgnoreCase(word)) {
                return vocab.getWord(targetLanguage);
            }
        }
        return null;
    }
}
