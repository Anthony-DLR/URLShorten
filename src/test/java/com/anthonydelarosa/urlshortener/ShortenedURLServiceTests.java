package com.anthonydelarosa.urlshortener;

import com.anthonydelarosa.urlshortener.entity.ShortenedURL;
import com.anthonydelarosa.urlshortener.repository.ShortenedURLTableRepository;
import com.anthonydelarosa.urlshortener.service.ShortenedURLService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;
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
        String shortUrl = "k70m8i21";
        ShortenedURL shortenedUrl = new ShortenedURL();
        shortenedUrl.setLongurl(url);
        shortenedUrl.setShortenedurl(shortUrl);
        shortenedUrl.setId(1);
        when(repo.save(any())).thenReturn(shortenedUrl);
        ShortenedURL result = this.service.saveURL(url);

        assertThat(shortenedUrl.getShortenedurl()).isEqualTo(result.getShortenedurl());
        assertThat(shortenedUrl.getLongurl()).isEqualTo(result.getLongurl());
        assertThat(shortenedUrl.getId()).isEqualTo(result.getId());
    }

    @Test
    public void getURLByShortTest() throws Exception {
        String url = "https://www.youtube.com/";
        String shortUrl = "k70m8i21";
        ShortenedURL shortenedUrl = new ShortenedURL();
        shortenedUrl.setLongurl(url);
        shortenedUrl.setShortenedurl(shortUrl);
        shortenedUrl.setId(1);
        when(repo.findByShortenedurl(any())).thenReturn(shortenedUrl);
        ShortenedURL result = this.service.getURLByShort(shortUrl);

        assertThat(shortenedUrl.getShortenedurl()).isEqualTo(result.getShortenedurl());
        assertThat(shortenedUrl.getLongurl()).isEqualTo(result.getLongurl());
        assertThat(shortenedUrl.getId()).isEqualTo(result.getId());
    }
}
