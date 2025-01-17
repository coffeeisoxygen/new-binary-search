package com.coffeecode.model.json;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.model.Vocabulary;

public class JsonParser {

    private final IJsonService jsonService;
    private static final Logger logger = LoggerFactory.getLogger(JsonParser.class);

    public JsonParser(IJsonService jsonService) {
        this.jsonService = jsonService;
    }

    public String readJsonFile(String filePath) throws IOException {
        return Files.readString(Path.of(filePath));
    }

    public List<Vocabulary> parseFile(String filePath) throws JsonParsingException {
        try {
            String jsonContent = Files.readString(Path.of(filePath));
            return jsonService.parseJson(jsonContent);
        } catch (IOException e) {
            String message = String.format("Failed to read file %s: %s", filePath, e.getMessage());
            logger.error(message, e);
            throw new JsonParsingException(message, e);
        }
    }
}
