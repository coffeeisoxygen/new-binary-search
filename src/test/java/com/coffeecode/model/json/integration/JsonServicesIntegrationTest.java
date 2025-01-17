package com.coffeecode.model.json.integration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.coffeecode.config.AppConfig;
import com.coffeecode.model.Vocabulary;
import com.coffeecode.model.json.service.JsonServices;
import com.coffeecode.model.json.service.JsonServicesFactory;

class JsonServicesIntegrationTest {

    private JsonServices jsonServices;
    private AppConfig config;

    @BeforeEach
    void setUp() {
        config = AppConfig.getDefault();
        jsonServices = JsonServicesFactory.create(config);
    }

    @Test
    void parseFile_ValidVocabularies_Success() {
        List<Vocabulary> result = jsonServices.parseFile(null);
        assertFalse(result.isEmpty());
    }
}
