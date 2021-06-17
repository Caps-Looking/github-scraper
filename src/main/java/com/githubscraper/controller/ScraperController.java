package com.githubscraper.controller;

import com.githubscraper.dto.RepoInfoDTO;
import com.githubscraper.service.ScraperService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/github-scraper")
public class ScraperController {

    private final ScraperService scraperService;

    public ScraperController(ScraperService scraperService) {
        this.scraperService = scraperService;
    }

    @GetMapping
    public List<RepoInfoDTO> getGithubInfos(@RequestParam String url) {
        return scraperService.getInfos(url);
    }
}
