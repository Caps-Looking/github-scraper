package com.githubscraper.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class RepoInfoDTO implements Serializable {

    private String extension;
    private int count;
    private int lines;
    private int bytes;
}
