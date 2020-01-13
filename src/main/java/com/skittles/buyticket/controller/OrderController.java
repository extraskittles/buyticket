package com.skittles.buyticket.controller;

import com.skittles.buyticket.detailModel.SceneDetail;
import com.skittles.buyticket.model.Cinema;
import com.skittles.buyticket.model.Movie;
import com.skittles.buyticket.param.ConfirmOrderParam;
import com.skittles.buyticket.result.CommonResult;
import com.skittles.buyticket.service.CinemaService;
import com.skittles.buyticket.service.OrderService;
import com.skittles.buyticket.utils.HttpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Api(tags = "用户查询电影信息以及购票")
@RequestMapping("/order")
@RestController
@CrossOrigin(origins = "*")
public class OrderController {
    @Autowired
    CinemaService cinemaService;
@Autowired
    OrderService orderService;
    @ApiOperation("根据电影院id查找该影院上映的所有场次的所有信息")
    @GetMapping("selectSceneByCinemaId")
    public CommonResult selectScene(int cinemaId) {
        List<SceneDetail> sceneDetails = cinemaService.selectSceneByCinemaId(cinemaId);
        if (sceneDetails != null) {
            return CommonResult.success(sceneDetails);
        }
        return CommonResult.failed();
    }
    @ApiOperation("查询所有影院的信息")
    @CrossOrigin(origins = "*")
    @GetMapping("/selectCinemas")
    public CommonResult selectCinemas(){
        List<Cinema> cinemas = cinemaService.selectCinemas();
        if(cinemas!=null){
            return CommonResult.success(cinemas);
        }
        return CommonResult.failed();
    }

    @ApiOperation("根据电影id查找有上映该电影的所有电影院信息（影院名字)")
    @CrossOrigin(origins = "*")
    @GetMapping("selectCinema")
    public CommonResult selectCinema(int movieId) {
        List<Cinema> cinemas = cinemaService.selectCinemaByMovieId(movieId);
        if (cinemas != null) {
            return CommonResult.success(cinemas);
        }
        return CommonResult.failed();
    }
    @ApiOperation("根据场次id,获取该场次所有信息")
    @GetMapping("/selectSceneById")
    public CommonResult selectSceneDetail(int sceneId) {
        SceneDetail sceneDetail = cinemaService.selectSceneDetailById(sceneId);
        return CommonResult.success(sceneDetail);
    }
    @ApiOperation("根据电影院id，场次id，座位号生成确认订单")
    @PostMapping("/confirmOrder")
    public CommonResult confirmOrder(ConfirmOrderParam confirmOrderParam, HttpServletRequest request) {
        int id = HttpUtils.getIdByRequest(request);
        String sitNumbers = confirmOrderParam.getSitNumbers();
        Map<String, Object> map = orderService.confirmOrder(confirmOrderParam, id);
        if(map.get("defaultTicket")!=null){
            return CommonResult.failed("所选票已被购，请再选票");
        }
        return CommonResult.success(map.get("orderDetail"));
    }

    @ApiOperation("根据用户积分支付")
    @PostMapping("/pay")
    public CommonResult pay(int orderId,HttpServletRequest request){
        Map<String, Boolean> map = orderService.pay(orderId, request);
        if(map.get("noPoints")){
            return CommonResult.failed("没有足够积分");
        }
        if(map.get("success")){
            return CommonResult.success(map.get("data"));
        }else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("查询所有电影的信息")
    @CrossOrigin(origins = "*")
    @GetMapping("/selectMovies")
    public CommonResult selectMovies(){
        List<Movie> movies = cinemaService.selectMovies();
        if(movies!=null){
            return CommonResult.success(movies);
        }else {
            return CommonResult.failed();
        }
    }
}
