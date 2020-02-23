package com.skittles.buyticket.mapper;

import com.skittles.buyticket.model.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface UserMapper {
    @Delete({
        "delete from user",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into user (id, name, ",
        "points, password, ",
        "roles, openid)",
        "values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
        "#{points,jdbcType=INTEGER}, #{password,jdbcType=VARCHAR}, ",
        "#{roles,jdbcType=VARCHAR}, #{openid,jdbcType=VARCHAR})"
    })
    @Options(useGeneratedKeys=true, keyProperty="id")
    int insert(User record);


    @InsertProvider(type=UserSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys=true, keyProperty="id")
    int insertSelective(User record);

    @Select({
        "select",
        "id, name, points, password, roles, openid",
        "from user",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="points", property="points", jdbcType=JdbcType.INTEGER),
        @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
        @Result(column="roles", property="roles", jdbcType=JdbcType.VARCHAR),
        @Result(column="openid", property="openid", jdbcType=JdbcType.VARCHAR)
    })
    User selectByPrimaryKey(Integer id);

    @UpdateProvider(type=UserSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(User record);

    @Update({
        "update user",
        "set name = #{name,jdbcType=VARCHAR},",
          "points = #{points,jdbcType=INTEGER},",
          "password = #{password,jdbcType=VARCHAR},",
          "roles = #{roles,jdbcType=VARCHAR},",
          "openid = #{openid,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(User record);

    @Select("select * from user where name=#{name}")
    User selectByUserName(String name);

    @Select("select * from user where openid=#{openid}")
    User selectByOpenid(String openid);
}