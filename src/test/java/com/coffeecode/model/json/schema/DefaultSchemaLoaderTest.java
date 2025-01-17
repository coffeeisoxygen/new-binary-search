package com.coffeecode.model.json.schema;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coffeecode.model.json.exception.JsonParsingException;
import com.coffeecode.model.json.resources.ResourceLoader;
import com.networknt.schema.JsonSchema;

@ExtendWith(MockitoExtension.class)
class DefaultSchemaLoaderTest {

    @Mock
    private ResourceLoader mockResourceLoader;
    private DefaultSchemaLoader schemaLoader;

    @BeforeEach
    void setUp() {
        schemaLoader = new DefaultSchemaLoader(mockResourceLoader);
    }

    @Test
    void loadSchema_ValidPath_ReturnsSchema() throws IOException {
        String validSchema = "{\"type\": \"array\"}";
        InputStream mockStream = new ByteArrayInputStream(validSchema.getBytes());
        when(mockResourceLoader.getResourceAsStream(anyString())).thenReturn(mockStream);

        JsonSchema schema = schemaLoader.loadSchema("/test/schema.json");
        assertNotNull(schema);
    }

    @Test
    void loadSchema_InvalidPath_ThrowsException() {
        when(mockResourceLoader.getResourceAsStream(anyString())).thenReturn(null);
        assertThrows(JsonParsingException.class,
                () -> schemaLoader.loadSchema("/invalid/path.json"));
    }
}
