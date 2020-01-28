package com.skittles.buyticket.mapper;

import com.skittles.buyticket.model.Cinema;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface CinemaMapper {
    @Delete({
        "delete from cinema",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into cinema (id, name, ",
        "address)",
        "values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
        "#{address,jdbcType=VARCHAR})"
    })
    int insert(Cinema record);

    @InsertProvider(type=CinemaSqlProvider.class, method="insertSelective")
    int insertSelective(Cinema record);

    @Select({
        "select",
        "id, name, address",
        "from cinema",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="address", property="address", jdbcType=JdbcType.VARCHAR)
    })
    Cinema selectByPrimaryKey(Integer id);

    @UpdateProvider(type=CinemaSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Cinema record);

    @Update({
        "update cinema",
        "set name = #{name,jdbcType=VARCHAR},",
          "address = #{address,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Cinema record);

    @Select("select * from cinema")
    List<Cinema> selectCinemas();
}