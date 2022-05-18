package com.anthonydelarosa.urlshortener.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.anthonydelarosa.urlshortener.entity.ShortenedURL;

public interface ShortenedURLTableRepository extends JpaRepository<ShortenedURL,Integer> {
    ShortenedURL findByShortenedurl(String shortened_url);
}
