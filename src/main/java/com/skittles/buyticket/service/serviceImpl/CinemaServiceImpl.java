package com.skittles.buyticket.service.serviceImpl;

import com.skittles.buyticket.detailMapper.OrderDetailMapper;
import com.skittles.buyticket.detailMapper.SceneDetailMapper;
import com.skittles.buyticket.detailModel.OrderDetail;
import com.skittles.buyticket.mapper.*;
import com.skittles.buyticket.detailModel.SceneDetail;
import com.skittles.buyticket.model.*;
import com.skittles.buyticket.param.ConfirmOrderParam;
import com.skittles.buyticket.param.ConfirmOrderParam2;
import com.skittles.buyticket.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;


import java.util.*;

@Service
@Transactional
public class CinemaServiceImpl implements CinemaService {
    @Autowired
    CinemaMapper cinemaMapper;
    @Autowired
    SceneMapper sceneMapper;
    @Autowired
    HallMapper hallMapper;
    @Autowired
    SceneDetailMapper sceneDetailMapper;
    @Autowired
    TicketOrderMapper orderMapper;
    @Autowired
    OrderDetailMapper orderDetailMapper;
    @Autowired
    MovieMapper movieMapper;

    @Override
    public int addCinema(Cinema cinema) {
        int insert = cinemaMapper.insert(cinema);
        return insert;
    }

    @Override
    public int addScene(Scene scene) {
        int insert = sceneMapper.insert(scene);
        return insert;
    }

    @Override
    public List<SceneDetail> selectSceneByCinemaId(int cinemaId) {
        /*  List<Scene> scenes = sceneMapper.selectSceneByCinemaId(cinemaId);*/
        List<SceneDetail> sceneDetails = sceneDetailMapper.selectSceneDetailByCinemaId(cinemaId);
        return sceneDetails;
    }

    @Override
    public List<Cinema> selectCinemaByMovieId(int movieId) {
        List<Cinema> cinemas = sceneMapper.selectCinemaByMovieId(movieId);
        return cinemas;

    }

    @Override
    public SceneDetail selectSceneDetailById(int sceneId) {
        SceneDetail sceneDetail = sceneDetailMapper.selectSceneDetailById(sceneId);
        return sceneDetail;
    }

    @Override
    public List<Movie> selectMovies() {
        List<Movie> movies= movieMapper.selectMovies();
        return movies;
    }

    @Override
    public List<Cinema> selectCinemas() {
        List<Cinema> cinemas = cinemaMapper.selectCinemas();
        return cinemas;
    }
}
