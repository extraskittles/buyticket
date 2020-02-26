package com.skittles.buyticket.mapper;

import com.skittles.buyticket.model.WechatUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface WechatUserMapper {
    @Delete({
            "delete from wechat_user",
            "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
            "insert into wechat_user (id, openid)",
            "values (#{id,jdbcType=INTEGER}, #{openid,jdbcType=VARCHAR})"
    })
    int insert(WechatUser record);

    @InsertProvider(type = WechatUserSqlProvider.class, method = "insertSelective")
    int insertSelective(WechatUser record);

    @Select({
            "select",
            "id, openid",
            "from wechat_user",
            "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "openid", property = "openid", jdbcType = JdbcType.VARCHAR)
    })
    WechatUser selectByPrimaryKey(Integer id);

    @UpdateProvider(type = WechatUserSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(WechatUser record);

    @Update({
            "update wechat_user",
            "set openid = #{openid,jdbcType=VARCHAR}",
            "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(WechatUser record);

    @Select("select * from wechat_user where openid=#{openid}")
    WechatUser selectByOpenid(String openid);
}