package com.skittles.buyticket.mapper;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.skittles.buyticket.model.Movie;

public class MovieSqlProvider {

    public String insertSelective(Movie record) {
        BEGIN();
        INSERT_INTO("movie");

        if (record.getId() != null) {
            VALUES("id", "#{id,jdbcType=INTEGER}");
        }

        if (record.getName() != null) {
            VALUES("name", "#{name,jdbcType=VARCHAR}");
        }

        if (record.getPrice() != null) {
            VALUES("price", "#{price,jdbcType=DOUBLE}");
        }

        if (record.getDescription() != null) {
            VALUES("description", "#{description,jdbcType=VARCHAR}");
        }

        if (record.getMovieLength() != null) {
            VALUES("movie_length", "#{movieLength,jdbcType=INTEGER}");
        }

        return SQL();
    }

    public String updateByPrimaryKeySelective(Movie record) {
        BEGIN();
        UPDATE("movie");

        if (record.getName() != null) {
            SET("name = #{name,jdbcType=VARCHAR}");
        }

        if (record.getPrice() != null) {
            SET("price = #{price,jdbcType=DOUBLE}");
        }

        if (record.getDescription() != null) {
            SET("description = #{description,jdbcType=VARCHAR}");
        }

        if (record.getMovieLength() != null) {
            SET("movie_length = #{movieLength,jdbcType=INTEGER}");
        }

        WHERE("id = #{id,jdbcType=INTEGER}");

        return SQL();
    }
}