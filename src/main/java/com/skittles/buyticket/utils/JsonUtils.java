package com.skittles.buyticket.utils;

import java.util.Map;

import com.google.gson.*;

public class JsonUtils {
        public static Map<String,Object> toMap(String json){
            Gson gson = new Gson();
            Map map = gson.fromJson(json, Map.class);
            return map;
        }
}
