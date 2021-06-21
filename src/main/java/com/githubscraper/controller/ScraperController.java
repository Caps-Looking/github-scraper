package com.githubscraper.controller;

import com.githubscraper.dto.RepoInfo;
import com.githubscraper.service.ScraperService;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation("Get information about github repository")
    public List<RepoInfo> getGithubInfos(@RequestParam String url) {
        return scraperService.getInfos(url);
    }
}
