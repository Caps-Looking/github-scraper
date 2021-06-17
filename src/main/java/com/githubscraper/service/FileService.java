package com.githubscraper.service;

import com.githubscraper.client.GithubClient;
import com.githubscraper.dto.Info;
import com.githubscraper.utils.ByteConverterUtils;
import com.githubscraper.utils.RegexUtils;
import com.githubscraper.utils.Consts;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class FileService {

    private final GithubClient githubClient;

    public FileService(GithubClient githubClient) {
        this.githubClient = githubClient;
    }

    public void extractFileInfos(Map<String, Info> extensions, List<String> files) {
        files.forEach(file -> {
            String extension = findExtension(file);
            createOrUpdateExtensions(extensions, extension);

            String fileHtml = githubClient.getHtml(Consts.GITHUB_URL + file);

            if (fileHtml.contains(Consts.IS_EXECUTABLE_FILE_REGEX)) {
                fileHtml = fileHtml.replace(Consts.EXECUTABLE_FILES, ""); // Removes the additional html
            }

            String lines = findLines(fileHtml);
            extensions.get(extension).addLines(Integer.parseInt(lines.trim()));

            String bytes = findBytes(fileHtml, lines).trim();
            extensions.get(extension).addBytes(convertToBytes(bytes));
        });
    }

    private int convertToBytes(String value) {
        try {
            value = RegexUtils.match(value, RegexUtils.createPattern(Consts.BYTES_REGEX));
            return Integer.parseInt(value);
        } catch (IllegalStateException e) {
            try {
                value = RegexUtils.match(value, RegexUtils.createPattern(Consts.MB_REGEX));
                return ByteConverterUtils.convertMb(value);
            } catch (IllegalStateException exception) {
                value = RegexUtils.match(value, RegexUtils.createPattern(Consts.KB_REGEX));
                return ByteConverterUtils.convertKb(value);
            }
        }
    }

    private void createOrUpdateExtensions(Map<String, Info> extensions, String extension) {
        if (!extensions.containsKey(extension)) {
            extensions.put(extension, new Info(1, 0, 0));
        } else {
            extensions.get(extension).addCounter();
        }
    }

    private String findBytes(String html, String lines) {
        if (lines.equals("0")) {
            return RegexUtils.match(html, RegexUtils.createPattern(Consts.GET_ZERO_BYTES_REGEX));
        }

        return RegexUtils.match(html, RegexUtils.createPattern(Consts.GET_BYTES_REGEX));
    }

    private String findExtension(String file) {
        String extension;
        try {
            extension = RegexUtils.match(file, RegexUtils.createPattern(Consts.EXTENSION_REGEX));
        } catch (IllegalStateException e) {
            extension = "undefined"; // Files that have no extension
        }

        String[] split = extension.split("\\.");
        return split[split.length - 1]; // Gets only the last extension (ex: given 'index.html.erb' returns 'erb')
    }

    private String findLines(String html) {
        try {
            return RegexUtils.match(html, RegexUtils.createPattern(Consts.LINES_REGEX));
        } catch (IllegalStateException e) {
            return "0"; // Files that have no lines
        }
    }
}
