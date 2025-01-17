package com.coffeecode.model.search;

import java.util.List;
import com.coffeecode.model.core.Language;
import com.coffeecode.model.core.Vocabulary;

public interface SearchStrategy {

    String findTranslation(List<Vocabulary> list,
            String word,
            Language sourceLanguage,
            Language targetLanguage);
}
