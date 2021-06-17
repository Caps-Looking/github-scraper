package com.githubscraper.client;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class GithubClient {

    private final OkHttpClient okHttpClient;

    public GithubClient() {
        this.okHttpClient = new OkHttpClient.Builder().build();
    }

    public String getHtml(String url) {
        Request request = new Request.Builder().url(url).build();
        try {
            Response execute = okHttpClient.newCall(request).execute();
            return Objects.requireNonNull(execute.body()).string();
        } catch (IOException e) {
            throw new RuntimeException("Request failed");
        }
    }
}
