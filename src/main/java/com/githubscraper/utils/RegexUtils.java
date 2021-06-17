package com.githubscraper.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

    public static Pattern createPattern(String regex) {
        return Pattern.compile(regex, Pattern.DOTALL);
    }

    public static String match(String value, Pattern regex) {
        Matcher matcher = regex.matcher(value);
        matcher.find();
        return matcher.group(1);
    }

}
