package com.anthonydelarosa.urlshortener;

import com.anthonydelarosa.urlshortener.entity.ClickDateTime;
import com.anthonydelarosa.urlshortener.entity.ShortenedURL;
import com.anthonydelarosa.urlshortener.repository.ClickDateTimeTableRepository;
import com.anthonydelarosa.urlshortener.service.ClickDateTimeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ClickDateTimeServiceTests {
    @Autowired
    ClickDateTimeService service;

    @MockBean
    ClickDateTimeTableRepository repo;

    @Test
    public void saveClickTest() throws Exception {
        String dateTime = "2022-05-19 00:18:17";
        ClickDateTime singleClick;
        singleClick = new ClickDateTime();
        singleClick.setDatetime(dateTime);
        singleClick.setShortenedurlid(1);
        when(repo.save(any())).thenReturn(singleClick);
        ClickDateTime result = this.service.saveClick(1);

        assertThat(singleClick.getShortenedurlid()).isEqualTo(result.getShortenedurlid());
        assertThat(singleClick.getDatetime()).isEqualTo(result.getDatetime());
    }

    @Test
    public void getClicksByShortenedURLIdTest() throws Exception {
        String dateTime = "2022-05-19 00:18:17";
        String secondDateTime = "2022-05-19 00:18:18";
        ClickDateTime singleClick;
        singleClick = new ClickDateTime();
        singleClick.setDatetime(dateTime);
        singleClick.setShortenedurlid(1);

        ClickDateTime secondClick;
        secondClick = new ClickDateTime();
        secondClick.setDatetime(secondDateTime);
        secondClick.setShortenedurlid(1);

        List<ClickDateTime> clicks = new ArrayList<>();
        clicks.add(singleClick);
        clicks.add(secondClick);
        when(repo.findByShortenedurlid(1)).thenReturn(clicks);
        List<ClickDateTime> result = this.service.getClicksByShortenedURLId(1);

        assertThat(clicks.size()).isEqualTo(result.size());
    }
}
