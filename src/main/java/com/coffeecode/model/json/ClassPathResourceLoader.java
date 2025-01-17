package com.coffeecode.model.json;

import java.io.InputStream;

public class ClassPathResourceLoader implements ResourceLoader {

    @Override
    public InputStream getResourceAsStream(String path) {
        return getClass().getResourceAsStream(path);
    }
}
