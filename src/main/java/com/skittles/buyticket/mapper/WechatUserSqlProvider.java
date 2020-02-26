package com.skittles.buyticket.mapper;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.skittles.buyticket.model.WechatUser;

public class WechatUserSqlProvider {

    public String insertSelective(WechatUser record) {
        BEGIN();
        INSERT_INTO("wechat_user");

        if (record.getId() != null) {
            VALUES("id", "#{id,jdbcType=INTEGER}");
        }

        if (record.getOpenid() != null) {
            VALUES("openid", "#{openid,jdbcType=VARCHAR}");
        }

        return SQL();
    }

    public String updateByPrimaryKeySelective(WechatUser record) {
        BEGIN();
        UPDATE("wechat_user");

        if (record.getOpenid() != null) {
            SET("openid = #{openid,jdbcType=VARCHAR}");
        }

        WHERE("id = #{id,jdbcType=INTEGER}");

        return SQL();
    }
}