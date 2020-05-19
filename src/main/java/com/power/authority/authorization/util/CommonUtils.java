package com.power.authority.authorization.util;

import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.util.Map;
import java.net.URL;

/**
 * @program: authorization
 * @description: 通用工具类
 * @author: xie ting
 * @create: 2020-05-15 09:20
 */
public class CommonUtils {

    //经纬度计算
    public static void getLatLon() {
        try {
            String address = "上海虹口区瑞虹路188号L1-21b";

            URL u = new URL("http://restapi.amap.com/v3/geocode/geo?address=" + address +
                    "&output=JSON&key=7c72029861365cb4eb6ec2b1d987a114");
            URLConnection yc = u.openConnection();
            //读取返回的数据
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
            String inputline = null;
            while ((inputline = in.readLine()) != null) {
                String json = "";
                json += (inputline);
                String jsonStr = json.toString();
                JSONObject jsonObject = JSONObject.fromObject(jsonStr);

                if (jsonObject.getJSONArray("geocodes").size() > 0) {
                    String ss = jsonObject.getJSONArray("geocodes").getJSONObject(0).get("location").toString();
                    //经度 、纬度
                    String lon = ss.split(",")[0];
                    String lat = ss.split(",")[1];
                    //更新
                    System.out.println("经度：" + lon + "纬度:" + lat);
                }
            }
            in.close();
//                LatitudeAndLongitude latAndLng = LngAndLatUtil.getLngAndLat(address);
//                if (latAndLng != null) {
//                    String lon = String.valueOf(latAndLng.getLongitude());
//                    String lat = String.valueOf(latAndLng.getLatitude());
//                    //更新
//                    setLatLon(shopid, lon, lat);
//                }
        } catch (Exception e) {
            String lon = "1.0";
            String lat = "0.0";
        }
    }

//    public static void main(String[] args){
//        getLatLon();
//    }

}
