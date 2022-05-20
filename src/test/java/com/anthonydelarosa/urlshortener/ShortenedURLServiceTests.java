package com.anthonydelarosa.urlshortener;

import com.anthonydelarosa.urlshortener.entity.ShortenedURL;
import com.anthonydelarosa.urlshortener.repository.ShortenedURLTableRepository;
import com.anthonydelarosa.urlshortener.service.ShortenedURLService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ShortenedURLServiceTests {
    @Autowired
    ShortenedURLService service;

    @MockBean
    ShortenedURLTableRepository repo;

    @Test
    public void saveUrlServiceTest() throws Exception {
        String url = "https://www.youtube.com/";
        ShortenedURL shortenedUrl = new ShortenedURL();
        shortenedUrl.setLongurl(url);
        shortenedUrl.setShortenedurl(any());
        when(repo.save(any())).thenReturn(shortenedUrl);

    }

}
