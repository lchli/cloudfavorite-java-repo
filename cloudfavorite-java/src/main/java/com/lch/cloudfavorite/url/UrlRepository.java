package com.lch.cloudfavorite.url;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UrlRepository extends JpaRepository<UrlEntity,String> {
    UrlEntity findByUid(String uid);
    List<UrlEntity> findByUserId(String userId);

}