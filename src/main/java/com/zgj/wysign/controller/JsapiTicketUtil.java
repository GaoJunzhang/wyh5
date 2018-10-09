package com.zgj.wysign.controller;

import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class JsapiTicketUtil {
    public static String sendGet(String url, String charset, int timeout) {
        String result = "";

        try {
            URL u = new URL(url);

            try {
                URLConnection conn = u.openConnection();
                conn.connect();
                conn.setConnectTimeout(timeout);
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));

                for(String line = ""; (line = in.readLine()) != null; result = result + line) {
                    ;
                }

                in.close();
                return result;
            } catch (IOException var8) {
                return result;
            }
        } catch (MalformedURLException var9) {
            return result;
        }
    }

    public static String getAccessToken() {
//        String appid = "wx15d6c7164b001194";
//        String appSecret = "bed03526b5fb21cb13dffd6551a47442";
        String appid = "wxa064dbde45927e3b";
        String appSecret = "74ee0fd5b464e0f1b4f286e6f810b895";
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + appSecret + "";
        String backData = sendGet(url, "utf-8", 10000);
        System.out.println(backData);
        String accessToken = (String) JSONObject.fromObject(backData).get("access_token");
        return accessToken;
    }

    public static String getJSApiTicket() {
        String acess_token = getAccessToken();
        System.out.println(getAccessToken());
        String urlStr = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + acess_token + "&type=jsapi";
        String backData = sendGet(urlStr, "utf-8", 10000);
        String ticket = (String)JSONObject.fromObject(backData).get("ticket");
        return ticket;
    }
}
