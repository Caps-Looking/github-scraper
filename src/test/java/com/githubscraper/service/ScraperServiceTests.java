package com.githubscraper.service;

import com.githubscraper.client.GithubClient;
import com.githubscraper.dto.RepoInfo;
import com.githubscraper.mocks.HtmlMocks;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static com.githubscraper.mocks.HtmlMocks.HOME_HTML;
import static org.mockito.Mockito.mock;

public class ScraperServiceTests {

    private static ScraperService scraperService;
    private static GithubClient githubClient;

    @BeforeAll
    public static void setup() {
        githubClient = mock(GithubClient.class);
        FileService fileService = new FileService(githubClient);
        scraperService = new ScraperService(githubClient, fileService);
    }

    @Test
    public void shouldReturnCorrectPayload() {
        String url = "https://github.com/my-user/my-app";
        Mockito.when(githubClient.getHtml(url + "/README.md")).thenReturn(HtmlMocks.README_HTML);
        Mockito.when(githubClient.getHtml(url + "/directory/package.json")).thenReturn(HtmlMocks.PACKAGE_HTML);
        Mockito.when(githubClient.getHtml(url + "/directory")).thenReturn(HtmlMocks.DIRECTORY_HTML);
        Mockito.when(githubClient.getHtml(url)).thenReturn(HOME_HTML);

        List<RepoInfo> execution = scraperService.getInfos(url);

        Assertions.assertThat(execution.get(0).getExtension()).isEqualTo("json");
        Assertions.assertThat(execution.get(0).getCount()).isEqualTo(1);
        Assertions.assertThat(execution.get(0).getLines()).isEqualTo(38);
        Assertions.assertThat(execution.get(0).getBytes()).isEqualTo(810);

        Assertions.assertThat(execution.get(1).getExtension()).isEqualTo("md");
        Assertions.assertThat(execution.get(1).getCount()).isEqualTo(1);
        Assertions.assertThat(execution.get(1).getLines()).isEqualTo(33);
        Assertions.assertThat(execution.get(1).getBytes()).isEqualTo(395);
    }

}
