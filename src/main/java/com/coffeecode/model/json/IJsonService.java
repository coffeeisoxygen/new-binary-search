package com.coffeecode.model.json;

import java.util.List;

import com.coffeecode.model.record.Vocabulary;

public interface IJsonService {

    List<Vocabulary> parseJson(String jsonContent) throws JsonParsingException;

    boolean validateJson(String jsonContent) throws JsonParsingException;
}
