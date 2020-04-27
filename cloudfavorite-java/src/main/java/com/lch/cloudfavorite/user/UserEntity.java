package com.lch.cloudfavorite.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Integer _id;

    public String uid;
    public String account;
    public String pwdMd5;
}
