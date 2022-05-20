package com.anthonydelarosa.urlshortener;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.anthonydelarosa.urlshortener.controller.URLShortenerController;
import com.anthonydelarosa.urlshortener.entity.ClickDateTime;
import com.anthonydelarosa.urlshortener.entity.ShortenedURL;
import com.anthonydelarosa.urlshortener.service.ClickDateTimeService;
import com.anthonydelarosa.urlshortener.service.ShortenedURLService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class UrlShortenerControllerTests {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private URLShortenerController controller;

	@MockBean
	private ShortenedURLService service;

	@MockBean
	private ClickDateTimeService clickService;

	@Test
	public void shouldReturnShortURLJson() throws Exception {
		String url = "https://www.youtube.com/";
		String shortUrl = "k70m8i21";
		ShortenedURL shortenedURL = new ShortenedURL();
		shortenedURL.setLongurl(url);
		shortenedURL.setShortenedurl("shortUrl");
		shortenedURL.setId(1);
		when(service.saveURL(url)).thenReturn(shortenedURL);
		MvcResult result = this.mockMvc.perform(post("/api/1/urlShortener").contentType("application/json").content("https://www.youtube.com/"))
				.andExpect(status().isOk()).andReturn();
		String contentString = result.getResponse().getContentAsString();
		ShortenedURL response = objectMapper.readValue(contentString, ShortenedURL.class);
		assertThat(shortenedURL.getShortenedurl()).isEqualTo(response.getShortenedurl());
		assertThat(shortenedURL.getLongurl()).isEqualTo(response.getLongurl());
		assertThat(shortenedURL.getId()).isEqualTo(response.getId());
	}

	@Test
	public void shouldReturnShortURLJson_InvalidUrl() throws Exception {
		String url = "www.youtube.com/";
		String shortUrl = "k70m8i21";
		ShortenedURL shortenedURL = new ShortenedURL();
		shortenedURL.setLongurl(url);
		shortenedURL.setShortenedurl("shortUrl");
		shortenedURL.setId(1);
		when(service.saveURL(url)).thenReturn(shortenedURL);
		MvcResult result = this.mockMvc.perform(post("/api/1/urlShortener").contentType("application/json").content(url))
				.andExpect(status().isBadRequest()).andReturn();
		String response = result.getResponse().getContentAsString();
		assertThat(response).isEqualTo("Invalid URL");
	}

	@Test
	public void shouldReturnLongURLJson() throws Exception {
		String url = "https://www.youtube.com/";
		String shortUrl = "k70m8i21";
		String dateTime = "2022-05-19 00:18:17";
		ShortenedURL shortenedURL = new ShortenedURL();
		shortenedURL.setLongurl(url);
		shortenedURL.setShortenedurl(shortUrl);
		shortenedURL.setId(1);

		ClickDateTime click = new ClickDateTime();
		click.setShortenedurlid(1);
		click.setDatetime(dateTime);
		click.setId(5);

		when(service.getURLByShort(shortUrl)).thenReturn(shortenedURL);
		when(clickService.saveClick(shortenedURL.getId(), dateTime)).thenReturn(click);

		this.mockMvc.perform(get("/api/1/urlShortener/" + shortUrl)).andDo(print())
				.andExpect(status().isFound()).andExpect(redirectedUrl(url));
	}

	@Test
	public void shouldReturnClicks() throws Exception {
		String url = "https://www.youtube.com/";
		String shortUrl = "k70m8i21";
		String dateTime = "2022-05-19 00:18:17";
		String secondDateTime = "2022-05-19 00:18:18";
		ShortenedURL shortenedURL = new ShortenedURL();
		shortenedURL.setLongurl(url);
		shortenedURL.setShortenedurl("shortUrl");
		shortenedURL.setId(1);

		ClickDateTime click = new ClickDateTime();
		click.setShortenedurlid(1);
		click.setDatetime(dateTime);
		click.setId(5);

		ClickDateTime secondClick = new ClickDateTime();
		click.setShortenedurlid(1);
		click.setDatetime(secondDateTime);
		click.setId(6);

		List<ClickDateTime> clicks = new ArrayList<>();
		clicks.add(click);
		clicks.add(secondClick);

		when(service.getURLByShort(shortUrl)).thenReturn(shortenedURL);
		when(clickService.getClicksByShortenedURLId(click.getShortenedurlid())).thenReturn(clicks);

		MvcResult result = this.mockMvc.perform(get("/api/1/urlShortener/clicks/" + shortUrl)).andDo(print()).andExpect(status().isOk()).andReturn();
		String contentString = result.getResponse().getContentAsString();
		List<ClickDateTime> response = Arrays.asList(objectMapper.readValue(contentString, ClickDateTime[].class));
	}

}
