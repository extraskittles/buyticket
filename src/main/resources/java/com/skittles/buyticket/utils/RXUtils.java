package com.skittles.buyticket.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RXUtils {
    //手机号格式
    public static boolean phoneNumMatch(String s) {
        Pattern pattern = Pattern.compile("^[1][358][0-9]{9}$");
        Matcher matcher = pattern.matcher(s);
        return matcher.find();
    }

    //用户名和密码6到12非空字符
    public static boolean nameMatch(String s) {
        Pattern pattern = Pattern.compile("^\\S{6,12}$");
        Matcher matcher = pattern.matcher(s);
        return matcher.find();
    }

    //邮箱格式
    public static boolean emailMatch(String s) {
        Pattern pattern = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
        Matcher matcher = pattern.matcher(s);
        return matcher.find();
    }
}
