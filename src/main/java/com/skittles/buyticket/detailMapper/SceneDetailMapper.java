package com.skittles.buyticket.detailMapper;

import com.skittles.buyticket.detailModel.SceneDetail;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SceneDetailMapper {

    //根据场次id查找该场次所有信息
    @Select("SELECT scene.*,movie.name AS movie_name,movie.price" +
            ",movie.description,movie.movie_length,cinema.name AS cinema_name,hall.sit_number,hall.name AS hall_name " +
            "FROM scene " +
            "LEFT JOIN cinema ON scene.cinema_id=cinema.id " +
            "LEFT JOIN movie ON scene.movie_id=movie.id " +
            "LEFT JOIN hall On scene.hall_id=hall.id " +
            "where scene.id=#{id} ORDER BY id;")
    SceneDetail selectSceneDetailById(int id);

    //根据电影院id，查找该电影院上映的所有场次的所有信息
    @Select("SELECT scene.*,movie.name AS movie_name,movie.price" +
            ",movie.description,movie.movie_length,cinema.name AS cinema_name,hall.sit_number,hall.name AS hall_name " +
            "FROM scene " +
            "LEFT JOIN cinema ON scene.cinema_id=cinema.id " +
            "LEFT JOIN movie ON scene.movie_id=movie.id " +
            "LEFT JOIN hall On scene.hall_id=hall.id " +
            "where cinema.id=#{id} ORDER BY id;")
    List<SceneDetail> selectSceneDetailByCinemaId(int cinemaId);
}
