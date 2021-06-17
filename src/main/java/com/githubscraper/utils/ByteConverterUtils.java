package com.githubscraper.utils;

public class ByteConverterUtils {

    public static int convertKb(String value) {
        Double doubleValue = Double.parseDouble(value) * 1024;
        return doubleValue.intValue();
    }

    public static int convertMb(String value) {
        Double doubleValue = Double.parseDouble(value) * 2048;
        return doubleValue.intValue();
    }
}
