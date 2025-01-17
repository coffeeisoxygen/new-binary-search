package com.coffeecode.model.json;

import java.io.InputStream;

public interface ResourceLoader {

    InputStream getResourceAsStream(String path);
}
