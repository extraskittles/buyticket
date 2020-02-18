package com.skittles.buyticket.controller;

import com.skittles.buyticket.detailMapper.OrderDetailMapper;
import com.skittles.buyticket.detailModel.OrderDetail;
import com.skittles.buyticket.detailModel.SceneDetail;
import com.skittles.buyticket.mapper.CinemaMapper;
import com.skittles.buyticket.mapper.MovieMapper;
import com.skittles.buyticket.model.Cinema;
import com.skittles.buyticket.model.Movie;
import com.skittles.buyticket.param.ConfirmOrderParam;
import com.skittles.buyticket.result.CommonResult;
import com.skittles.buyticket.result.ResultCode;
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
@Autowired
    OrderDetailMapper orderDetailMapper;
@Autowired
    MovieMapper movieMapper;
@Autowired
    CinemaMapper cinemaMapper;
    @ApiOperation("根据电影院id查找该影院上映的所有有效场次的所有信息")
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
    @GetMapping(value = "/selectSceneById")
    public CommonResult selectSceneDetail( Integer sceneId) {
        SceneDetail sceneDetail = cinemaService.selectSceneDetailById(sceneId);
        return CommonResult.success(sceneDetail);
    }
    @ApiOperation("根据电影院id，场次id，座位号生成确认订单")
    @PostMapping(value = "/confirmOrder",produces = "application/json")
    public CommonResult confirmOrder(@RequestBody ConfirmOrderParam confirmOrderParam, HttpServletRequest request) {
        int id = HttpUtils.getIdByRequest(request);
        if(id==0){
           return CommonResult.unAuthorized();
        }
        Map<String, Object> map = orderService.confirmOrder(confirmOrderParam, id);
        if(map.get("defaultTicket")!=null){
            return CommonResult.failed("所选票已被购，请再选票");
        }
        return CommonResult.success(map.get("orderDetail"));
    }

    @ApiOperation("根据用户积分支付")
    @PostMapping(value = "/pay",produces = "application/json")
    public CommonResult pay(@RequestBody int orderId,HttpServletRequest request){
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

    @ApiOperation("根据订单号查询该订单信息")
    @GetMapping("/selectOrderById")
    public CommonResult selectOrderById(int orderId,HttpServletRequest request){
        Integer userId = HttpUtils.getIdByRequest(request);
        OrderDetail orderDetail = orderDetailMapper.selectOrderDetailById(orderId);
        if(userId!=null&&userId==orderDetail.getUserId()){
            return CommonResult.success(orderDetail);
        }else{
            return CommonResult.failed("查询订单错误");
        }
    }

    @ApiOperation("根据电影id查询该电影的信息")
    @GetMapping("/selectMovieById")
    public CommonResult selectOrderById(Integer movieId){
        if(movieId==null){
            return CommonResult.failed("电影id不能为空");
        }
        Movie movie = movieMapper.selectByPrimaryKey(movieId);
        if(movie!=null){
            return CommonResult.success(movie);
        }else{
            return CommonResult.success("查询不到相关电影");
        }
    }

    @ApiOperation("根据影院id查询该影院的信息")
    @GetMapping("/selectCinemaById")
    public CommonResult selectCinemaById(Integer cinemaId){
        if(cinemaId==null){
            return CommonResult.failed("影院id不能为空");
        }
        Cinema cinema = cinemaMapper.selectByPrimaryKey(cinemaId);
        if(cinema!=null){
            return CommonResult.success(cinema);
        }else{
            return CommonResult.success("查询不到相关影院");
        }
    }

    @ApiOperation("根据影院id查询该影院上映了哪些电影")
    @GetMapping("/selectMoviesByCinemaId")
    public CommonResult selectMoviesByCinemaId(Integer cinemaId){
        List<Movie> movies = movieMapper.selectMoviesByCinemaId(cinemaId);
        if(movies!=null){
            return CommonResult.success(movies);
        }else {
            return CommonResult.failed("查询失败");
        }
    }



}
