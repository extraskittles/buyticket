package com.skittles.buyticket.mapper;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.skittles.buyticket.model.TicketOrder;

public class TicketOrderSqlProvider {

    public String insertSelective(TicketOrder record) {
        BEGIN();
        INSERT_INTO("ticket_order");

        if (record.getId() != null) {
            VALUES("id", "#{id,jdbcType=INTEGER}");
        }

        if (record.getUserId() != null) {
            VALUES("user_id", "#{userId,jdbcType=INTEGER}");
        }

        if (record.getStatus() != null) {
            VALUES("status", "#{status,jdbcType=VARCHAR}");
        }

        if (record.getCinemaId() != null) {
            VALUES("cinema_id", "#{cinemaId,jdbcType=INTEGER}");
        }

        if (record.getSceneId() != null) {
            VALUES("scene_id", "#{sceneId,jdbcType=INTEGER}");
        }

        if (record.getSitNumbers() != null) {
            VALUES("sit_numbers", "#{sitNumbers,jdbcType=VARCHAR}");
        }

        if (record.getPayPrice() != null) {
            VALUES("pay_price", "#{payPrice,jdbcType=DOUBLE}");
        }

        if (record.getUuid() != null) {
            VALUES("uuid", "#{uuid,jdbcType=VARCHAR}");
        }

        return SQL();
    }

    public String updateByPrimaryKeySelective(TicketOrder record) {
        BEGIN();
        UPDATE("ticket_order");

        if (record.getUserId() != null) {
            SET("user_id = #{userId,jdbcType=INTEGER}");
        }

        if (record.getStatus() != null) {
            SET("status = #{status,jdbcType=VARCHAR}");
        }

        if (record.getCinemaId() != null) {
            SET("cinema_id = #{cinemaId,jdbcType=INTEGER}");
        }

        if (record.getSceneId() != null) {
            SET("scene_id = #{sceneId,jdbcType=INTEGER}");
        }

        if (record.getSitNumbers() != null) {
            SET("sit_numbers = #{sitNumbers,jdbcType=VARCHAR}");
        }

        if (record.getPayPrice() != null) {
            SET("pay_price = #{payPrice,jdbcType=DOUBLE}");
        }

        if (record.getUuid() != null) {
            SET("uuid = #{uuid,jdbcType=VARCHAR}");
        }

        WHERE("id = #{id,jdbcType=INTEGER}");

        return SQL();
    }
}