package com.anthonydelarosa.urlshortener;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.anthonydelarosa.urlshortener.controller.URLShortenerController;
import com.anthonydelarosa.urlshortener.entity.ShortenedURL;
import com.anthonydelarosa.urlshortener.service.ShortenedURLService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
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

	@Test
	public void shouldReturnShortURLJson() throws Exception {
		String url = "https://www.youtube.com/";
		ShortenedURL shortURL = new ShortenedURL();
		shortURL.setLongurl(url);
		shortURL.setShortenedurl("k70m8i21");
		shortURL.setId(1);
		when(service.saveURL(url)).thenReturn(shortURL);
		MvcResult result = this.mockMvc.perform(post("/urlShortener/").contentType("application/json").content("https://www.youtube.com/"))
				.andExpect(status().isOk()).andReturn();
		String contentString = result.getResponse().getContentAsString();
		ShortenedURL response = objectMapper.readValue(contentString, ShortenedURL.class);
		assertThat(shortURL.getShortenedurl()).isEqualTo(response.getShortenedurl());
	}

	@Test
	public void shouldReturnLongURLJson() throws Exception {
		this.mockMvc.perform(get("/urlShortener/")).andDo(print()).andExpect(status().isOk()).andReturn();
	}

	@Test
	public void shouldReturnClicks() throws Exception {
		this.mockMvc.perform(get("/urlShortener/clicks/")).andDo(print()).andExpect(status().isOk()).andReturn();
	}

}
