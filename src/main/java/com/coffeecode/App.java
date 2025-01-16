package com.coffeecode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        logger.info("Hello World!");
        logger.debug("This is a debug message");
        logger.error("This is an error message");
        logger.warn("This is a warn message");
        logger.trace("This is a trace message");
        try {
            int unused = 10 / 0;
        } catch (ArithmeticException e) {
            logger.error("An exception occurred: ", e);
        }
    }
}
