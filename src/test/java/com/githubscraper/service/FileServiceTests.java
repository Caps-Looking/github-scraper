package com.githubscraper.service;

import com.githubscraper.client.GithubClient;
import com.githubscraper.dto.Info;
import com.githubscraper.mocks.HtmlMocks;
import com.githubscraper.utils.Consts;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;

public class FileServiceTests {

    private static FileService fileService;
    private static GithubClient githubClient;

    @BeforeAll
    public static void setup() {
        githubClient = mock(GithubClient.class);
        fileService = new FileService(githubClient);
    }

    @Test
    public void shouldReturnCorrectInfos() {
        Map<String, Info> extensions = new HashMap<>();
        List<String> files = new ArrayList<>();
        files.add("/master/README.md");
        files.add("/master/package.json");

        Mockito.when(githubClient.getHtml(Consts.GITHUB_URL + "/master/README.md")).thenReturn(HtmlMocks.README_HTML);
        Mockito.when(githubClient.getHtml(Consts.GITHUB_URL + "/master/package.json")).thenReturn(HtmlMocks.PACKAGE_HTML);

        fileService.extractFileInfos(extensions, files);

        Assertions.assertThat(extensions.containsKey("md")).isTrue();
        Assertions.assertThat(extensions.containsKey("json")).isTrue();

        Assertions.assertThat(extensions.get("md").getBytes()).isEqualTo(395);
        Assertions.assertThat(extensions.get("json").getBytes()).isEqualTo(810);

        Assertions.assertThat(extensions.get("md").getCount()).isEqualTo(1);
        Assertions.assertThat(extensions.get("json").getCount()).isEqualTo(1);

        Assertions.assertThat(extensions.get("md").getLines()).isEqualTo(33);
        Assertions.assertThat(extensions.get("json").getLines()).isEqualTo(38);
    }

    @Test
    public void shouldCovertToBytes() {
        Map<String, Info> extensions = new HashMap<>();
        List<String> files = new ArrayList<>();
        files.add("/master/README.md");
        files.add("/master/package.json");

        Mockito.when(githubClient.getHtml(Consts.GITHUB_URL + "/master/README.md")).thenReturn(HtmlMocks.README_MB_HTML);
        Mockito.when(githubClient.getHtml(Consts.GITHUB_URL + "/master/package.json")).thenReturn(HtmlMocks.PACKAGE_KB_HTML);

        fileService.extractFileInfos(extensions, files);

        Assertions.assertThat(extensions.get("md").getBytes()).isEqualTo(808960);
        Assertions.assertThat(extensions.get("json").getBytes()).isEqualTo(829440);
    }

    @Test
    public void shouldReturnUndefinedWhenFileHasNoExtension() {
        Map<String, Info> extensions = new HashMap<>();
        List<String> files = new ArrayList<>();
        files.add("/master/README");

        Mockito.when(githubClient.getHtml(Consts.GITHUB_URL + "/master/README")).thenReturn(HtmlMocks.README_HTML);

        fileService.extractFileInfos(extensions, files);

        Assertions.assertThat(extensions.containsKey("undefined")).isTrue();
    }

    @Test
    public void shouldReturnZeroWhenFileHasNoLines() {
        Map<String, Info> extensions = new HashMap<>();
        List<String> files = new ArrayList<>();
        files.add("/master/README.md");

        Mockito.when(githubClient.getHtml(Consts.GITHUB_URL + "/master/README.md")).thenReturn(HtmlMocks.NO_LINES_HTML);

        fileService.extractFileInfos(extensions, files);

        Assertions.assertThat(extensions.get("md").getLines()).isEqualTo(0);
    }

    @Test
    public void shouldReturnCorrectValuesWhenFileIsExecutable() {
        Map<String, Info> extensions = new HashMap<>();
        List<String> files = new ArrayList<>();
        files.add("/master/README.md");

        Mockito.when(githubClient.getHtml(Consts.GITHUB_URL + "/master/README.md")).thenReturn(HtmlMocks.EXECUTABLE_FILE);

        fileService.extractFileInfos(extensions, files);

        Assertions.assertThat(extensions.containsKey("md")).isTrue();
        Assertions.assertThat(extensions.get("md").getBytes()).isEqualTo(395);
        Assertions.assertThat(extensions.get("md").getCount()).isEqualTo(1);
        Assertions.assertThat(extensions.get("md").getLines()).isEqualTo(33);
    }

    @Test
    public void shouldReturnTwoWhenHasTwoFilesWithSameExtension() {
        Map<String, Info> extensions = new HashMap<>();
        List<String> files = new ArrayList<>();
        files.add("/master/README.md");
        files.add("/master/README2.md");

        Mockito.when(githubClient.getHtml(Consts.GITHUB_URL + "/master/README.md")).thenReturn(HtmlMocks.README_HTML);
        Mockito.when(githubClient.getHtml(Consts.GITHUB_URL + "/master/README2.md")).thenReturn(HtmlMocks.README_HTML);

        fileService.extractFileInfos(extensions, files);

        Assertions.assertThat(extensions.get("md").getCount()).isEqualTo(2);
    }
}
