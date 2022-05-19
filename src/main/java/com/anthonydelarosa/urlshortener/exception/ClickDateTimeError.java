package com.anthonydelarosa.urlshortener.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClickDateTimeError {
    private final String message;
}
