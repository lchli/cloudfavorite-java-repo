package com.lch.cloudfavorite.url

import org.apache.ibatis.annotations.*
import org.springframework.stereotype.Component

@Mapper
@Component
interface UrlMapper {
    //mybatis不会自动生成表。
    @Select("SELECT * FROM url_entity WHERE userId = #{userId}")
    fun findByUserId(@Param("userId") userId: String):  List<UrlEntity>?

    @Select("SELECT * FROM url_entity WHERE uid = #{uid}")
    fun findByUid(@Param("uid") uid: String): UrlEntity?

    @Insert("insert into url_entity(uid,title,url,userId,createDate) values " +
            "(#{uid},#{title},#{url},#{userId},#{createDate})")
    @Options(useGeneratedKeys = true, keyProperty = "_id", keyColumn = "_id")
    fun save(user: UrlEntity): Int

    @Select("DELETE FROM url_entity WHERE uid = #{uid}")
    fun deleteByUid(@Param("uid") uid: String): Int?

    @Select("SELECT * FROM url_entity")
    fun findAll():  List<UrlEntity>?

    @Update("update url_entity set title=#{title},url=#{url},createDate=#{createDate} where uid = #{uid}")
    fun update(user: UrlEntity): Int
}