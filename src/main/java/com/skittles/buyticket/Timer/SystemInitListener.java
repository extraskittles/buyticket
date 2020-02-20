package com.skittles.buyticket.Timer;

import com.skittles.buyticket.detailMapper.OrderDetailMapper;
import com.skittles.buyticket.mapper.SceneMapper;
import com.skittles.buyticket.mapper.TicketOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
//项目启动后每天0点更新数据库
@Component
public class SystemInitListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    SceneMapper sceneMapper;
    @Autowired
    TicketOrderMapper orderMapper;
    @Autowired
    DataTask dataTask;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //删除场次表所有数据
        sceneMapper.deleteAll();
        orderMapper.deleteAll();
        //插入今天，明天的数据
        dataTask.updateTodayData();
        dataTask.updateTomorrowData();
        Timer timer = new Timer();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,1);
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE),0,0,0);
        long interval=24*60*60*1000;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                dataTask.updateTomorrowData();
            }
        },calendar.getTime(),interval);
    }
}
