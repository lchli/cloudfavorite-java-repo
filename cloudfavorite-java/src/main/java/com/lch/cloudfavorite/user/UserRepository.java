package com.lch.cloudfavorite.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,String> {
    //根据账号查询用户信息
    UserEntity findByUid(String userId);
    UserEntity findByAccount(String account);

}