package com.skittles.buyticket.detailMapper;

import com.skittles.buyticket.detailModel.OrderDetail;
import org.apache.ibatis.annotations.Select;

public interface OrderDetailMapper {
    @Select("SELECT \n" +
            "ticket_order.*,\n" +
            "scene.datetime,scene.name AS scene_name,\n" +
            "movie.name AS movie_name,movie.price,movie.movie_length,movie.description,\n" +
            "cinema.name AS cinema_name,\n" +
            "hall.sit_number,hall.name AS hall_name,hall.id AS hall_id \n" +
            "FROM ticket_order\n" +
            "LEFT JOIN scene ON ticket_order.scene_id=scene.id \n" +
            "LEFT JOIN movie ON scene.movie_id=movie.id\n" +
            "LEFT JOIN hall On scene.hall_id=hall.id\n" +
            "LEFT JOIN cinema On scene.cinema_id=cinema.id\n" +
            "where ticket_order.id=#{id}")
    OrderDetail selectOrderDetailById(int id);
}
