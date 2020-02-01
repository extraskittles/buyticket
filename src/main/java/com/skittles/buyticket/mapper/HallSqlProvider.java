package com.skittles.buyticket.mapper;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.skittles.buyticket.model.Hall;

public class HallSqlProvider {

    public String insertSelective(Hall record) {
        BEGIN();
        INSERT_INTO("hall");
        
        if (record.getId() != null) {
            VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getSitNumber() != null) {
            VALUES("sit_number", "#{sitNumber,jdbcType=INTEGER}");
        }
        
        if (record.getName() != null) {
            VALUES("name", "#{name,jdbcType=VARCHAR}");
        }
        
        if (record.getCinemaId() != null) {
            VALUES("cinema_id", "#{cinemaId,jdbcType=INTEGER}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(Hall record) {
        BEGIN();
        UPDATE("hall");
        
        if (record.getSitNumber() != null) {
            SET("sit_number = #{sitNumber,jdbcType=INTEGER}");
        }
        
        if (record.getName() != null) {
            SET("name = #{name,jdbcType=VARCHAR}");
        }
        
        if (record.getCinemaId() != null) {
            SET("cinema_id = #{cinemaId,jdbcType=INTEGER}");
        }
        
        WHERE("id = #{id,jdbcType=INTEGER}");
        
        return SQL();
    }
}