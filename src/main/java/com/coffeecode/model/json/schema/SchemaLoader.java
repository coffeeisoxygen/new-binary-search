package com.coffeecode.model.json.schema;

import com.networknt.schema.JsonSchema;

public interface SchemaLoader {
    JsonSchema loadSchema(String schemaPath);
}
