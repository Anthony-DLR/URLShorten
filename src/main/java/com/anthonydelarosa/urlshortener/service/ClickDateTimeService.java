package com.anthonydelarosa.urlshortener.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.anthonydelarosa.urlshortener.repository.ClickDateTimeTableRepository;
import com.anthonydelarosa.urlshortener.entity.ClickDateTime;
import java.util.List;

@Service
public class ClickDateTimeService {

    @Autowired
    private ClickDateTimeTableRepository clickDateTimeTableRepository;

    public ClickDateTime saveClick(ClickDateTime click) {return clickDateTimeTableRepository.save(click);}

    public List<ClickDateTime> getClicksByShortenedURLId (int shortenedurlid) {
        return clickDateTimeTableRepository.findByShortenedurlid(shortenedurlid);
    }
}
