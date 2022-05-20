package com.anthonydelarosa.urlshortener.service;

import com.anthonydelarosa.urlshortener.model.ClickDateTimeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.anthonydelarosa.urlshortener.repository.ClickDateTimeTableRepository;
import com.anthonydelarosa.urlshortener.entity.ClickDateTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ClickDateTimeService {

    @Autowired
    private ClickDateTimeTableRepository clickDateTimeTableRepository;

    public ClickDateTime saveClick(int id) {
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(dt);

        ClickDateTimeModel click = ClickDateTimeModel.create(id, currentTime);
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
