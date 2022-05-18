package com.anthonydelarosa.urlshortener.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.anthonydelarosa.urlshortener.repository.ShortenedURLTableRepository;
import com.anthonydelarosa.urlshortener.entity.ShortenedURL;

@Service
public class ShortenedURLService {

    @Autowired
    private ShortenedURLTableRepository shortenedURLTableRepository;

    public ShortenedURL saveURL(ShortenedURL product) {
        return shortenedURLTableRepository.save(product);
    }

    public ShortenedURL getURLByShort(String shortened_url) {
        return shortenedURLTableRepository.findByShortenedurl(shortened_url);
    }
}
