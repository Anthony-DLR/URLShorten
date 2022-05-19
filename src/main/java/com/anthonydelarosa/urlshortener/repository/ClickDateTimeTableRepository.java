package com.anthonydelarosa.urlshortener.repository;

import com.anthonydelarosa.urlshortener.entity.ClickDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClickDateTimeTableRepository extends JpaRepository<ClickDateTime,Integer> {
    List<ClickDateTime> findByShortenedurlid(int shortenedurlid);
}
