package com.githubscraper.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class Node {

    private String url;
    private List<Node> directories;
}
