package com.skittles.buyticket.service;

import com.skittles.buyticket.model.Cinema;
import com.skittles.buyticket.model.Movie;
import com.skittles.buyticket.model.Scene;
import com.skittles.buyticket.detailModel.SceneDetail;
import com.skittles.buyticket.param.ConfirmOrderParam;

import java.util.List;
import java.util.Map;

public interface CinemaService {
    int addCinema(Cinema cinema);
    int addScene(Scene scene);
    List<SceneDetail> selectSceneByCinemaId(int cinemaId);
    List<Cinema>selectCinemaByMovieId(int movieId);
    SceneDetail selectSceneDetailById(int SceneId);
    List<Movie> selectMovies();
    List<Cinema> selectCinemas();
}
