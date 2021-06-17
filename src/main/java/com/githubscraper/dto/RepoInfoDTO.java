package com.githubscraper.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RepoInfoDTO {

    private String extension;
    private int count;
    private int lines;
    private int bytes;
}
