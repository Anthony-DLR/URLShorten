package com.anthonydelarosa.urlshortener.controller;

import com.anthonydelarosa.urlshortener.model.ShortenedURLModel;
import com.anthonydelarosa.urlshortener.model.ClickDateTimeModel;
import com.anthonydelarosa.urlshortener.service.ClickDateTimeService;
import org.json.simple.JSONObject;
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
import com.anthonydelarosa.urlshortener.entity.ClickDateTime;
import com.anthonydelarosa.urlshortener.service.ShortenedURLService;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping(value = "/urlShortener")
public class URLShortenerController {

    @Autowired
    private ShortenedURLService service;

    @Autowired
    private ClickDateTimeService clickService;

    private ShortenedURL shortURL;
    private ClickDateTime singleClick;
    private JSONObject response;


    @PostMapping
    public ResponseEntity create(@RequestBody final String url) {
        final UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https"});
        if (!urlValidator.isValid(url)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Invalid URL");
        }
        final ShortenedURLModel shortened = ShortenedURLModel.create(url);
        log.info("URL id generated = {}", shortened.getId());
        this.shortURL = new ShortenedURL();
        this.shortURL.setLongurl(url);
        this.shortURL.setShortenedurl(shortened.getId());

        return ResponseEntity.ok(service.saveURL(shortURL));

    }

    @GetMapping(value = "/{shortUrl}")
    public ResponseEntity find(@PathVariable final String shortUrl) {
        ShortenedURL databaseFetch = service.getURLByShort(shortUrl);

        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(dt);

        if(databaseFetch == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("ShortURL Not Found");
        }

        ClickDateTimeModel click = ClickDateTimeModel.create(databaseFetch.getId(), currentTime);
        this.singleClick = new ClickDateTime();
        this.singleClick.setDatetime(click.getDateTime());
        this.singleClick.setShortenedurlid(click.getShortenedurlid());

        clickService.saveClick(singleClick);
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
