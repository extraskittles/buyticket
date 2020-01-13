package com.skittles.buyticket.mapper;

import com.skittles.buyticket.model.Hall;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface HallMapper {
    @Delete({
        "delete from hall",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into hall (id, sit_number, ",
        "name, left_sit)",
        "values (#{id,jdbcType=INTEGER}, #{sitNumber,jdbcType=INTEGER}, ",
        "#{name,jdbcType=VARCHAR}, #{leftSit,jdbcType=VARCHAR})"
    })
    int insert(Hall record);

    @InsertProvider(type=HallSqlProvider.class, method="insertSelective")
    int insertSelective(Hall record);

    @Select({
        "select",
        "id, sit_number, name, left_sit",
        "from hall",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="sit_number", property="sitNumber", jdbcType=JdbcType.INTEGER),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="left_sit", property="leftSit", jdbcType=JdbcType.VARCHAR)
    })
    Hall selectByPrimaryKey(Integer id);

    @UpdateProvider(type=HallSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Hall record);

    @Update({
        "update hall",
        "set sit_number = #{sitNumber,jdbcType=INTEGER},",
          "name = #{name,jdbcType=VARCHAR},",
          "left_sit = #{leftSit,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Hall record);
}