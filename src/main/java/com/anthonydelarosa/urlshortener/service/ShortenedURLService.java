package com.anthonydelarosa.urlshortener.service;

import com.anthonydelarosa.urlshortener.model.ShortenedURLModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.anthonydelarosa.urlshortener.repository.ShortenedURLTableRepository;
import com.anthonydelarosa.urlshortener.entity.ShortenedURL;

@Service
public class ShortenedURLService {

    @Autowired
    private ShortenedURLTableRepository shortenedURLTableRepository;

    public ShortenedURL saveURL(String url) {
        final ShortenedURLModel shortened = ShortenedURLModel.create(url);
        ShortenedURL shortenedUrl = new ShortenedURL();
        shortenedUrl.setLongurl(url);
        shortenedUrl.setShortenedurl(shortened.getId());

        return shortenedURLTableRepository.save(shortenedUrl);
    }

    public ShortenedURL getURLByShort(String shortened_url) {
        return shortenedURLTableRepository.findByShortenedurl(shortened_url);
    }
}
