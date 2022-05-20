package com.anthonydelarosa.urlshortener.service;

import com.anthonydelarosa.urlshortener.model.ClickDateTimeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.anthonydelarosa.urlshortener.repository.ClickDateTimeTableRepository;
import com.anthonydelarosa.urlshortener.entity.ClickDateTime;
import java.util.List;

@Service
public class ClickDateTimeService {

    @Autowired
    private ClickDateTimeTableRepository clickDateTimeTableRepository;

    public ClickDateTime saveClick(int id, String time) {
        ClickDateTimeModel click = ClickDateTimeModel.create(id, time);
        ClickDateTime singleClick;
        singleClick = new ClickDateTime();
        singleClick.setDatetime(click.getDateTime());
        singleClick.setShortenedurlid(click.getShortenedurlid());
        return clickDateTimeTableRepository.save(singleClick);
    }

    public List<ClickDateTime> getClicksByShortenedURLId (int shortenedurlid) {
        return clickDateTimeTableRepository.findByShortenedurlid(shortenedurlid);
    }
}
