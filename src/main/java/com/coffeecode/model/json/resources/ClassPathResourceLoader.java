package com.coffeecode.model.json.resources;

import java.io.InputStream;

public class ClassPathResourceLoader implements ResourceLoader {

    @Override
    public InputStream getResourceAsStream(String path) {
        return getClass().getResourceAsStream(path);
    }
}
