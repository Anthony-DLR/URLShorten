package com.anthonydelarosa.urlshortener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class URLShortenerController {
    @GetMapping("/")
    public String index() {
        return "Testing First URl Shortener End Point";
    }
}
