package com.skittles.buyticket.test;


import com.skittles.buyticket.utils.SmsUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestMain {
       @Test
    public void test(){
           SmsUtils smsUtils = new SmsUtils();
           smsUtils.send("13724043328","222333");
       }
}
