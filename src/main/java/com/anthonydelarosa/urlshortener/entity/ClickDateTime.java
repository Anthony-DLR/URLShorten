package com.anthonydelarosa.urlshortener.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shortened_url_clicks")
public class ClickDateTime {
    @Id
    @GeneratedValue
    private int id;
    private String datetime;
    private int shortenedurlid;
}
