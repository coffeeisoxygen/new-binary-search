package com.coffeecode.model.search;

import java.util.List;
import com.coffeecode.model.core.Language;
import com.coffeecode.model.core.Vocabulary;
import com.coffeecode.model.search.context.SearchTracker;

public interface SearchStrategy {
    String findTranslation(List<Vocabulary> list, String word,
            Language sourceLanguage, Language targetLanguage, 
            @Nullable SearchTracker tracker);
}