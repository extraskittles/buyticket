package com.skittles.buyticket.controller;

import com.skittles.buyticket.model.Cinema;
import com.skittles.buyticket.model.Scene;
import com.skittles.buyticket.detailModel.SceneDetail;
import com.skittles.buyticket.result.CommonResult;
import com.skittles.buyticket.service.CinemaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "管理员对电影院信息的操作")
@RestController
@RequestMapping("/cinema")
public class CinemaController {
    @Autowired
    CinemaService cinemaService;

    @ApiOperation("添加影院的信息")
    @PostMapping("/addCinema")
    public CommonResult addCinema(Cinema cinema) {
        int i = cinemaService.addCinema(cinema);
        if (i == 0) {
            return CommonResult.failed();
        } else {
            return CommonResult.success();
        }
    }

    @ApiOperation("添加某影院场次")
    @PostMapping("/addScene")
    public CommonResult addScene(Scene scene) {
        int i = cinemaService.addScene(scene);
        if (i == 0) {
            return CommonResult.failed();
        } else {
            return CommonResult.success();
        }
    }

}
