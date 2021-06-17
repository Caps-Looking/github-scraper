package com.githubscraper.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Info {

    private int count;
    private int lines;
    private int bytes;

    public void addCounter() {
        this.count++;
    }

    public void addLines(int lines) {
        this.lines = this.lines + lines;
    }

    public void addBytes(int bytes) {
        this.bytes = this.bytes + bytes;
    }
}
