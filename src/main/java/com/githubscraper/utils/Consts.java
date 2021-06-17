package com.githubscraper.utils;

public class Consts {

    public static final String GITHUB_URL = "https://github.com";
    public static final String EXECUTABLE_FILES = "\n" +
            "      <span class=\"file-mode\" title=\"File mode\">executable file</span>\n" +
            "      <span class=\"file-info-divider\"></span>\n" +
            "      ";

    // Regex
    public static final String BYTES_REGEX = "(.*?) Bytes";
    public static final String DIRECTORY_REGEX = "<svg aria-label=\"Directory\"(.*?)</a></span>";
    public static final String EXTENSION_REGEX = "\\.(.*)";
    public static final String FILE_REGEX = "<svg aria-label=\"File\"(.*?)</a></span>";
    public static final String GET_BYTES_REGEX = "<span class=\"file-info-divider\"></span>(.*?)<";
    public static final String GET_ZERO_BYTES_REGEX = "<div class=\"text-mono f6 flex-auto pr-3 flex-order-2 flex-md-order-1\">(.*?)<";
    public static final String IS_EXECUTABLE_FILE_REGEX = "executable file";
    public static final String KB_REGEX = "(.*?) KB";
    public static final String LINES_REGEX = "<div class=\"text-mono f6 flex-auto pr-3 flex-order-2 flex-md-order-1\">(.*?) lines";
    public static final String LINK_REGEX = "href=\"(.*?)\"";
    public static final String MB_REGEX = "(.*?) MB";

}
