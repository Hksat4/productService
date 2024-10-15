package com.MyProduct.service;

import org.springframework.stereotype.Service;

@Service
public class DataParserFactory {

    public DataParser getParser(String format) {
        if ("csv".equalsIgnoreCase(format)) {
            return new CsvDataParser();
        }
        throw new IllegalArgumentException("Unsupported format: " + format);
    }
}
