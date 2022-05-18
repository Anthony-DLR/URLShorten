package com.anthonydelarosa.urlshortener.model;

import com.google.common.hash.Hashing;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.RandomStringUtils;

import java.nio.charset.StandardCharsets;




@Getter
@AllArgsConstructor
public class ShortenedURLModel {
    private final String id;
    private final String url;

    public static ShortenedURLModel create(final String url) {
        RandomStringUtils.randomAlphanumeric(8).toLowerCase();
        final String id = RandomStringUtils.randomAlphanumeric(8).toLowerCase();
        return new ShortenedURLModel(id, url);
    }
}
