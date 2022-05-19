package com.anthonydelarosa.urlshortener.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClickDateTimeModel {
    private final int shortenedurlid;
    private final String dateTime;

    public static ClickDateTimeModel create(final int shortenedurlid, final String dateTime) {
        return new ClickDateTimeModel(shortenedurlid, dateTime);
    }
}
