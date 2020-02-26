package com.skittles.buyticket.mapper;

import com.skittles.buyticket.model.Cinema;
import com.skittles.buyticket.model.Scene;
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

public interface SceneMapper {
    @Delete({
            "delete from scene",
            "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
            "insert into scene (id, name, ",
            "datetime, movie_id, ",
            "cinema_id, hall_id, ",
            "left_sit)",
            "values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
            "#{datetime,jdbcType=TIMESTAMP}, #{movieId,jdbcType=INTEGER}, ",
            "#{cinemaId,jdbcType=INTEGER}, #{hallId,jdbcType=INTEGER}, ",
            "#{leftSit,jdbcType=VARCHAR})"
    })
    int insert(Scene record);

    @InsertProvider(type = SceneSqlProvider.class, method = "insertSelective")
    int insertSelective(Scene record);

    @Select({
            "select",
            "id, name, datetime, movie_id, cinema_id, hall_id, left_sit",
            "from scene",
            "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "datetime", property = "datetime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "movie_id", property = "movieId", jdbcType = JdbcType.INTEGER),
            @Result(column = "cinema_id", property = "cinemaId", jdbcType = JdbcType.INTEGER),
            @Result(column = "hall_id", property = "hallId", jdbcType = JdbcType.INTEGER),
            @Result(column = "left_sit", property = "leftSit", jdbcType = JdbcType.VARCHAR)
    })
    Scene selectByPrimaryKey(Integer id);

    @UpdateProvider(type = SceneSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Scene record);

    @Update({
            "update scene",
            "set name = #{name,jdbcType=VARCHAR},",
            "datetime = #{datetime,jdbcType=TIMESTAMP},",
            "movie_id = #{movieId,jdbcType=INTEGER},",
            "cinema_id = #{cinemaId,jdbcType=INTEGER},",
            "hall_id = #{hallId,jdbcType=INTEGER},",
            "left_sit = #{leftSit,jdbcType=VARCHAR}",
            "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Scene record);

    //删除所有场次信息
    @Delete("delete from scene")
    void deleteAll();

    @Select("select * from scene where cinema_id=#{cinemaId}")
    List<Scene> selectSceneByCinemaId(int cinemaId);

    @Select("SELECT cinema.* FROM scene LEFT JOIN cinema ON scene.cinema_id=cinema.id WHERE movie_id =#{movieId} GROUP BY id;")
    List<Cinema> selectCinemaByMovieId(int movieId);
}