package com.skittles.buyticket.utils;

import com.nimbusds.jose.util.IntegerUtils;
import io.netty.util.internal.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {
    //根据数字生成1到该数字的字符创，如3则  1,2,3
    public static String sitNumberStr(int number){
        String s="1";
        for(int i=1;i<number;i++){
           s=s+","+(i+1);
        }
        return s;
    }
}
