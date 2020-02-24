package com.skittles.buyticket.mapper;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.skittles.buyticket.model.Scene;

public class SceneSqlProvider {

    public String insertSelective(Scene record) {
        BEGIN();
        INSERT_INTO("scene");

        if (record.getId() != null) {
            VALUES("id", "#{id,jdbcType=INTEGER}");
        }

        if (record.getName() != null) {
            VALUES("name", "#{name,jdbcType=VARCHAR}");
        }

        if (record.getDatetime() != null) {
            VALUES("datetime", "#{datetime,jdbcType=TIMESTAMP}");
        }

        if (record.getMovieId() != null) {
            VALUES("movie_id", "#{movieId,jdbcType=INTEGER}");
        }

        if (record.getCinemaId() != null) {
            VALUES("cinema_id", "#{cinemaId,jdbcType=INTEGER}");
        }

        if (record.getHallId() != null) {
            VALUES("hall_id", "#{hallId,jdbcType=INTEGER}");
        }

        if (record.getLeftSit() != null) {
            VALUES("left_sit", "#{leftSit,jdbcType=VARCHAR}");
        }

        return SQL();
    }

    public String updateByPrimaryKeySelective(Scene record) {
        BEGIN();
        UPDATE("scene");

        if (record.getName() != null) {
            SET("name = #{name,jdbcType=VARCHAR}");
        }

        if (record.getDatetime() != null) {
            SET("datetime = #{datetime,jdbcType=TIMESTAMP}");
        }

        if (record.getMovieId() != null) {
            SET("movie_id = #{movieId,jdbcType=INTEGER}");
        }

        if (record.getCinemaId() != null) {
            SET("cinema_id = #{cinemaId,jdbcType=INTEGER}");
        }

        if (record.getHallId() != null) {
            SET("hall_id = #{hallId,jdbcType=INTEGER}");
        }

        if (record.getLeftSit() != null) {
            SET("left_sit = #{leftSit,jdbcType=VARCHAR}");
        }

        WHERE("id = #{id,jdbcType=INTEGER}");

        return SQL();
    }
}