package com.coffeecode.model.json;

import java.util.List;

import com.coffeecode.model.Vocabulary;

/**
 * Interface for JSON processing services. Handles loading and parsing of
 * vocabulary data.
 */
public interface IJsonService {

    /**
     * Parses vocabulary from specified file path
     *
     * @param filePath Path to JSON file
     * @return List of vocabulary entries
     * @throws JsonParsingException if parsing fails
     */
    List<Vocabulary> parseFile(String filePath) throws JsonParsingException;

    /**
     * Validates JSON content against schema
     *
     * @param jsonContent JSON string to validate
     * @return true if valid, false otherwise
     */
    boolean isValid(String jsonContent);
}
