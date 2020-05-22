package com.skittles.buyticket.Timer;

import java.util.Timer;
import java.util.TimerTask;

public class DataTimer {
    //
    public static void updateData() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("test");
            }
        }, 100, 1000);
    }
}
