package com.skittles.buyticket.mapper;

import com.skittles.buyticket.model.Movie;
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

public interface MovieMapper {
    @Delete({
        "delete from movie",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into movie (id, name, ",
        "price, description, ",
        "movie_length)",
        "values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
        "#{price,jdbcType=DOUBLE}, #{description,jdbcType=VARCHAR}, ",
        "#{movieLength,jdbcType=INTEGER})"
    })
    int insert(Movie record);

    @InsertProvider(type=MovieSqlProvider.class, method="insertSelective")
    int insertSelective(Movie record);

    @Select({
        "select",
        "id, name, price, description, movie_length",
        "from movie",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="price", property="price", jdbcType=JdbcType.DOUBLE),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="movie_length", property="movieLength", jdbcType=JdbcType.INTEGER)
    })
    Movie selectByPrimaryKey(Integer id);

    @UpdateProvider(type=MovieSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Movie record);

    @Update({
        "update movie",
        "set name = #{name,jdbcType=VARCHAR},",
          "price = #{price,jdbcType=DOUBLE},",
          "description = #{description,jdbcType=VARCHAR},",
          "movie_length = #{movieLength,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Movie record);

    @Select("select * from movie ORDER BY id DESC lIMIT 0,10")
    List<Movie> selectMovies();
}