package com.skittles.buyticket.utils;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import net.minidev.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    private static final byte[] secret = "skittlesskittlesskittlesskittlesskittles".getBytes();

    //生成token
    public static String createToken(Map<String, Object> payloadMap) throws JOSEException {
        long validTime = 24 * 3600 * 1000;
        long exp = System.currentTimeMillis() + validTime;
        payloadMap.put("exp", exp);
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);
        Payload payload = new Payload(new JSONObject(payloadMap));
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        MACSigner macSigner = new MACSigner(secret);
        jwsObject.sign(macSigner);
        return jwsObject.serialize();
    }

    //对称加密方式验证token
    public static Map<String, Object> verifyToken(String token) throws JOSEException {
        Map<String, Object> resultMap = new HashMap();
        JWSObject jwsObject = null;
        try {
            jwsObject = JWSObject.parse(token);
        } catch (Exception e) {
            resultMap.put("code", -1);
            resultMap.put("msg", "认证失败");
            return resultMap;
        }
        Payload payload = jwsObject.getPayload();
        MACVerifier macVerifier = new MACVerifier(secret);
        if (jwsObject.verify(macVerifier)) {
            JSONObject jsonObject = payload.toJSONObject();
            if (jsonObject.containsKey("exp")) {
                Long expTime = Long.valueOf(jsonObject.get("exp").toString());
                Long nowTime = System.currentTimeMillis();
                if (expTime > nowTime) {
                    resultMap.put("code", 1);
                    resultMap.put("msg", "认证成功");
                } else {
                    resultMap.put("code", 2);
                    resultMap.put("msg", "认证已过期");
                }
            }
        } else {
            resultMap.put("code", -1);
            resultMap.put("msg", "认证失败");
        }
        return resultMap;
    }
//根据token获取payload
    public static Map<String, Object> getPayLord(String token) throws Exception {
        String roles = JWSObject.parse(token).getPayload().toJSONObject().get("roles").toString();
        int id = Integer.valueOf(JWSObject.parse(token).getPayload().toJSONObject().get("id").toString());
        Map<String,Object> map =new HashMap<>();
        map.put("roles",roles);
        map.put("id",id);
        return map;
    }
}