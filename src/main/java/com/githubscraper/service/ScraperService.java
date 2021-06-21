package com.githubscraper.service;

import com.githubscraper.client.GithubClient;
import com.githubscraper.dto.Info;
import com.githubscraper.dto.Node;
import com.githubscraper.dto.RepoInfo;
import com.githubscraper.utils.Consts;
import com.githubscraper.utils.RegexUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

@Service
public class ScraperService {

    private final GithubClient githubClient;
    private final FileService fileService;

    public ScraperService(GithubClient githubClient, FileService fileService) {
        this.githubClient = githubClient;
        this.fileService = fileService;
    }

    @Cacheable(cacheNames = "Repo", key = "#url")
    public List<RepoInfo> getInfos(String url) {
        Map<String, Info> extensions = new HashMap<>();
        Node rootNode = new Node(url, new ArrayList<>());

        extractInfos(rootNode, extensions);
        return buildRepoInfos(extensions);
    }

    @NotNull
    private List<RepoInfo> buildRepoInfos(Map<String, Info> extensions) {
        List<RepoInfo> repoInfos = new ArrayList<>();
        extensions.forEach((key, info) -> repoInfos.add(new RepoInfo(key, info.getCount(), info.getLines(), info.getBytes())));
        repoInfos.sort(Comparator.comparing(RepoInfo::getExtension));
        return repoInfos;
    }

    private void extractInfos(Node rootNode, Map<String, Info> extensions) {
        String html = githubClient.getHtml(rootNode.getUrl());

        List<String> files = findRows(html, Consts.FILE_REGEX);
        fileService.extractFileInfos(extensions, files);

        List<String> directories = findRows(html, Consts.DIRECTORY_REGEX);
        directories.forEach(directory -> {
            Node node = new Node(Consts.GITHUB_URL + directory, new ArrayList<>());
            rootNode.getDirectories().add(node);
            extractInfos(node, extensions);
        });
    }

    private List<String> extractLinks(List<String> htmls) {
        return htmls.stream()
                .map(html -> RegexUtils.match(html, RegexUtils.createPattern(Consts.LINK_REGEX)))
                .collect(Collectors.toList());
    }

    private List<String> findRows(String html, String pattern) {
        List<String> rows = new ArrayList<>();
        final Matcher matcher = RegexUtils.createPattern(pattern).matcher(html);
        while (matcher.find()) {
            rows.add(matcher.group(1));
        }
        return extractLinks(rows);
    }
}
