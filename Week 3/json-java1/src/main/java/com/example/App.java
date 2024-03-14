package com.example;


/**
 * Hello world!
 *
 */
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.*;

public class App {

    public static void main(String[] args) {
        //Tạo đối tượng JSON, lấy giá trị và chuyển sang string 
        JSONObject obj = new JSONObject();

        obj.put("name","foo");
        obj.put("num",100);
        obj.put("balance",1000.21);
        obj.put("is_vip",true);

        System.out.println(obj.get("num"));
        System.out.println(obj.length());
        
        String jsonText = obj.toString();
        System.out.println(jsonText);


        //Dùng gson để chuyển trực tiếp từ string sang object tương ứng
        String json = "{ \"name\": \"foo\", \"num\": 100 }";

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

        System.out.println("Name: " + jsonObject.get("name"));
        System.out.println("Num: " + jsonObject.get("num"));
    }
}



