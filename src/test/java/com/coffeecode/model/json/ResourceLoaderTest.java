package com.coffeecode.model.json;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.coffeecode.model.json.resources.ClassPathResourceLoader;
import com.coffeecode.model.json.resources.ResourceLoader;

@ExtendWith(MockitoExtension.class)
class ResourceLoaderTest {

    private ResourceLoader loader;
    private static final String TEST_PATH = "/data/vocabularies.json";

    @BeforeEach
    void setUp() {
        loader = new ClassPathResourceLoader();
    }

    @Test
    void loadResource_ValidPath_ReturnsInputStream() {
        // Act
        InputStream result = loader.getResourceAsStream(TEST_PATH);

        // Assert
        assertNotNull(result);
    }
}
