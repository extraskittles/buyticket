package com.skittles.buyticket.mapper;

import com.skittles.buyticket.model.Merchant;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface MerchantMapper {
    @Delete({
        "delete from merchant",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into merchant (id, name, ",
        "password)",
        "values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
        "#{password,jdbcType=VARCHAR})"
    })
    int insert(Merchant record);

    @InsertProvider(type=MerchantSqlProvider.class, method="insertSelective")
    int insertSelective(Merchant record);

    @Select({
        "select",
        "id, name, password",
        "from merchant",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR)
    })
    Merchant selectByPrimaryKey(Integer id);

    @UpdateProvider(type=MerchantSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Merchant record);

    @Update({
        "update merchant",
        "set name = #{name,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Merchant record);
}