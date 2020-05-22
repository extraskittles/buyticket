package com.skittles.buyticket.mapper;

import com.skittles.buyticket.model.TicketOrder;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface TicketOrderMapper {
    @Delete({
            "delete from ticket_order",
            "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
            "insert into ticket_order (id, user_id, ",
            "status, cinema_id, ",
            "scene_id, sit_numbers, ",
            "pay_price, uuid)",
            "values (#{id,jdbcType=INTEGER}, #{userId,jdbcType=INTEGER}, ",
            "#{status,jdbcType=VARCHAR}, #{cinemaId,jdbcType=INTEGER}, ",
            "#{sceneId,jdbcType=INTEGER}, #{sitNumbers,jdbcType=VARCHAR}, ",
            "#{payPrice,jdbcType=DOUBLE}, #{uuid,jdbcType=VARCHAR})"
    })
    int insert(TicketOrder record);

    @InsertProvider(type = TicketOrderSqlProvider.class, method = "insertSelective")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertSelective(TicketOrder record);

    @Select({
            "select",
            "id, user_id, status, cinema_id, scene_id, sit_numbers, pay_price, uuid",
            "from ticket_order",
            "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.INTEGER),
            @Result(column = "status", property = "status", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cinema_id", property = "cinemaId", jdbcType = JdbcType.INTEGER),
            @Result(column = "scene_id", property = "sceneId", jdbcType = JdbcType.INTEGER),
            @Result(column = "sit_numbers", property = "sitNumbers", jdbcType = JdbcType.VARCHAR),
            @Result(column = "pay_price", property = "payPrice", jdbcType = JdbcType.DOUBLE),
            @Result(column = "uuid", property = "uuid", jdbcType = JdbcType.VARCHAR)
    })
    TicketOrder selectByPrimaryKey(Integer id);

    @UpdateProvider(type = TicketOrderSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(TicketOrder record);

    @Update({
            "update ticket_order",
            "set user_id = #{userId,jdbcType=INTEGER},",
            "status = #{status,jdbcType=VARCHAR},",
            "cinema_id = #{cinemaId,jdbcType=INTEGER},",
            "scene_id = #{sceneId,jdbcType=INTEGER},",
            "sit_numbers = #{sitNumbers,jdbcType=VARCHAR},",
            "pay_price = #{payPrice,jdbcType=DOUBLE},",
            "uuid = #{uuid,jdbcType=VARCHAR}",
            "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TicketOrder record);

    //删除表所有数据
    @Delete({
            "delete from ticket_order"
    })
    int deleteAll();


}