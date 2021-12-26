package com.epidemic.util;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Map;

public class DataGsonLisr {


    public static ArrayList dataGsonList(String jsonString){

        Gson gson = new Gson();
        Map map =gson.fromJson(jsonString, Map.class);

        String data = (String) map.get("data");
        Map subMap = gson.fromJson(data, Map.class);

        ArrayList areaList = (ArrayList) subMap.get("areaTree");
        Map dataMap = (Map) areaList.get(0);
        ArrayList childrenList = (ArrayList) dataMap.get("children");


        return childrenList;
    }
}
