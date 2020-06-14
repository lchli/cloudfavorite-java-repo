create database if not exists `cloud_fav` default character set utf8 collate utf8_general_ci;

CREATE TABLE if not exists user_entity
(
    _id     INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    account VARCHAR(255) NOT NULL,
    pwdMd5  VARCHAR(255) NOT NULL,
    uid     VARCHAR(255) NOT NULL
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

CREATE TABLE if not exists url_entity
(
    _id        INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    title      VARCHAR(255) NOT NULL,
    userId     VARCHAR(255) NOT NULL,
    url        TEXT         NOT NULL,
    uid        VARCHAR(255) NOT NULL,
    createDate LONG         NOT NULL
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;