package com.anthonydelarosa.urlshortener.controller;

import com.anthonydelarosa.urlshortener.service.ClickDateTimeService;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.anthonydelarosa.urlshortener.entity.ShortenedURL;
import com.anthonydelarosa.urlshortener.entity.ClickDateTime;
import com.anthonydelarosa.urlshortener.service.ShortenedURLService;
import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/1/urlShortener")
public class URLShortenerController {

    @Autowired
    private ShortenedURLService service;

    @Autowired
    private ClickDateTimeService clickService;

    private ShortenedURL shortURL;

    private JSONObject response;


    @PostMapping
    public ResponseEntity create(@RequestBody final String url) {
        final UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https"});
        if (!urlValidator.isValid(url)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Invalid URL");
        }

        return ResponseEntity.ok(service.saveURL(url));
    }

    @GetMapping(value = "/{shortUrl}")
    public ResponseEntity find(@PathVariable final String shortUrl) {
        ShortenedURL databaseFetch = service.getURLByShort(shortUrl);

        if(databaseFetch == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("ShortURL Not Found");
        }

        clickService.saveClick(databaseFetch.getId());
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(databaseFetch.getLongurl()))
                .build();
    }

    @RequestMapping(value = "/clicks/{shortUrl}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getClicks(@PathVariable final String shortUrl) {
        ShortenedURL databaseFetch = service.getURLByShort(shortUrl);
        if(databaseFetch == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("ShortURL Not Found");
        }

        List<ClickDateTime> clicks = clickService.getClicksByShortenedURLId(databaseFetch.getId());

        return ResponseEntity.ok(clicks);
    }
}
