package com.lch.cloudfavorite.user

import org.apache.ibatis.annotations.*
import org.springframework.stereotype.Component

@Mapper
@Component
interface UserMapper {
    //mybatis不会自动生成表。
    @Select("SELECT * FROM user_entity WHERE account = #{account}")
    fun findByAccount(@Param("account") account: String): UserEntity?

    @Select("SELECT * FROM user_entity WHERE uid = #{uid}")
    fun findByUid(@Param("uid") uid: String): UserEntity?

    @Insert("insert into user_entity(uid,account,pwdMd5) values " +
            "(#{uid},#{account},#{pwdMd5})")
    @Options(useGeneratedKeys = true, keyProperty = "_id", keyColumn = "_id")
    fun save(user: UserEntity): Int
}