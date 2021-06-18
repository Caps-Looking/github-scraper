package com.githubscraper.exceptions;

public class ClientException extends RuntimeException {
    public ClientException() {
        super("Request failed");
    }
}
