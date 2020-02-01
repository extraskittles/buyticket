package com.skittles.buyticket.Timer;

import com.skittles.buyticket.mapper.SceneMapper;
import com.skittles.buyticket.model.Scene;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
@Component
public class DataTask {
    @Autowired
    SceneMapper sceneMapper;
    @Test
    public void updateTodayData() {
        //插入当天的的场次信息
        Scene scene = new Scene();
        Date date = new Date();
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        long time=date.getTime()+(long)(6*60*60*1000);
        int sceneCount=1;
        //遍历3间影院
        for(int h=1;h<4;h++){
            scene.setCinemaId(h);
            //遍历该影院的3个影厅
            for(int j=1;j<4;j++){
                scene.setMovieId(4-j);
                if(h==1){
                    scene.setHallId(j);
                }
                if(h==2){
                    scene.setHallId(3+j);
                }
                if(h==3){
                    scene.setHallId(6+j);
                }
                //遍历每个影厅的8个时间段
                for(int k=1;k<9;k++){
                    time=time+(long)(2*60*60*1000);
                    date.setTime(time);
                    if(h==1){
                        scene.setName("光大影院场次"+sceneCount);
                    }else if(h==2){
                        scene.setName("皓天影院场次"+sceneCount);
                    }else if(h==3){
                        scene.setName("勒流影院场次"+sceneCount);
                    }
                    sceneCount++;
                    scene.setDatetime(date);
                    sceneMapper.insertSelective(scene);
                }
                date.setHours(0);
                date.setMinutes(0);
                date.setSeconds(0);
                //遍历完一个厅后，让time回到6:20或者6:40,6:00
                if(j==1){
                    time=date.getTime()+(long)(6*60*60*1000+20*60*1000);
                }else if(j==2){
                    time=date.getTime()+(long)(6*60*60*1000+40*60*1000);
                }else if(j==3){
                    time=date.getTime()+(long)(6*60*60*1000);
                }
            }
            sceneCount=1;
        }
    }
@Test
    public void updateTomorrowData(){
        //插入当天的的场次信息
        Scene scene = new Scene();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,1);
        Date date = calendar.getTime();
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        long time=date.getTime()+(long)(6*60*60*1000);
        int sceneCount=1;
        //遍历3间影院
        for(int h=1;h<4;h++){
            scene.setCinemaId(h);
            //遍历该影院的3个影厅
            for(int j=1;j<4;j++){
                if(h==1){
                    scene.setHallId(j);
                }
                if(h==2){
                    scene.setHallId(3+j);
                }
                if(h==3){
                    scene.setHallId(6+j);
                }
                scene.setMovieId(4-j);
                //遍历每个影厅的8个时间段
                for(int k=1;k<9;k++){
                    time=time+(long)(2*60*60*1000);
                    date.setTime(time);
                    if(h==1){
                        scene.setName("光大影院场次"+sceneCount);
                    }else if(h==2){
                        scene.setName("皓天影院场次"+sceneCount);
                    }else if(h==3){
                        scene.setName("勒流影院场次"+sceneCount);
                    }
                    sceneCount++;
                    scene.setDatetime(date);
                    sceneMapper.insertSelective(scene);
                }
                date.setHours(0);
                date.setMinutes(0);
                date.setSeconds(0);
                //遍历完一个厅后，让time回到6:20或者6:40,6:00
                if(j==1){
                    time=date.getTime()+(long)(6*60*60*1000+20*60*1000);
                }else if(j==2){
                    time=date.getTime()+(long)(6*60*60*1000+40*60*1000);
                }else if(j==3){
                    time=date.getTime()+(long)(6*60*60*1000);
                }
            }
            sceneCount=1;
        }

    }
}
