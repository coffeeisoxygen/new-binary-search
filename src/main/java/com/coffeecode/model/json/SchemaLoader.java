package com.coffeecode.model.json;

import com.networknt.schema.JsonSchema;

public interface SchemaLoader {
    JsonSchema loadSchema(String schemaPath);
}
