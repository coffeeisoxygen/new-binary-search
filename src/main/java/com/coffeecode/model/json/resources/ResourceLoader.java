package com.coffeecode.model.json.resources;

import java.io.InputStream;

public interface ResourceLoader {

    InputStream getResourceAsStream(String path);
}
