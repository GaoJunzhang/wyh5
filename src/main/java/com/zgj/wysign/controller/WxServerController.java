package com.zgj.wysign.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@RestController
@RequestMapping({"/api/web"})
public class WxServerController {
    @GetMapping({"/getSign"})
    public Map wxSign(String url) {
        Map map = new HashMap();
        String jsapi_ticket = JsapiTicketUtil.getJSApiTicket();
//        String url = "http://nw.incg.net/";
        Map<String, String> ret = sign(jsapi_ticket, url);
        map.put("appId", "wxa064dbde45927e3b");
        Iterator var5 = ret.entrySet().iterator();

        while(var5.hasNext()) {
            Map.Entry entry = (Map.Entry)var5.next();
            System.out.println(entry.getKey() + ", " + entry.getValue());
            map.put(entry.getKey(), entry.getValue());
        }

        return map;
    }
    public static Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String signature = "";
        String string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;
        System.out.println(string1);

        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException var8) {
            var8.printStackTrace();
        } catch (UnsupportedEncodingException var9) {
            var9.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        return ret;
    }

    private static String byteToHex(byte[] hash) {
        Formatter formatter = new Formatter();
        byte[] var2 = hash;
        int var3 = hash.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            byte b = var2[var4];
            formatter.format("%02x", b);
        }

        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000L);
    }
}
