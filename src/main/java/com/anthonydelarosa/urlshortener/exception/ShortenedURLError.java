package com.anthonydelarosa.urlshortener.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShortenedURLError {
    private final String message;
}
