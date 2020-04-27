package com.lch.cloudfavorite.url;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UrlEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Integer _id;

    public String uid;
    public String title;
    public String url;
    public String userId;
    public long createDate;
}
