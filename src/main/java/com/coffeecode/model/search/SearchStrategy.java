package com.coffeecode.model.search;

import java.util.List;

import com.coffeecode.model.Language;
import com.coffeecode.model.Vocabulary;

public interface SearchStrategy {

    String findTranslation(List<Vocabulary> list,
            String word,
            Language sourceLanguage,
            Language targetLanguage);
}
