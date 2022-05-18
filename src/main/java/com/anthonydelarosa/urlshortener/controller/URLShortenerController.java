package com.anthonydelarosa.urlshortener.controller;

import com.anthonydelarosa.urlshortener.model.ShortenedURLModel;
import com.anthonydelarosa.urlshortener.exception.ShortenedURLError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.anthonydelarosa.urlshortener.entity.ShortenedURL;
import com.anthonydelarosa.urlshortener.service.ShortenedURLService;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping(value = "/urlShortener")
public class URLShortenerController {

    @Autowired
    private ShortenedURLService service;

    private ShortenedURL shortURL;

    @PostMapping
    public ShortenedURL create(@RequestBody final String url) {
        final UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https"});
        /***if (!urlValidator.isValid(url)) {
            return ResponseEntity.badRequest().body(new ShortenedURLError("Invalid URL."));
        }***/
        final ShortenedURLModel shortened = ShortenedURLModel.create(url);
        log.info("URL id generated = {}", shortened.getId());
        this.shortURL = new ShortenedURL();
        this.shortURL.setLong_url(url);
        this.shortURL.setShortened_url(shortened.getId());

        return service.saveURL(shortURL);
    }
}
