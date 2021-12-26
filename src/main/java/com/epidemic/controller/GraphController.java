package com.epidemic.controller;


import com.epidemic.bean.DataBean;
import com.epidemic.bean.GraphBarBean;
import com.epidemic.bean.MapBean;
import com.epidemic.service.DataService;
import com.google.gson.Gson;
import org.springframework.ui.Model;
import com.epidemic.bean.GraphBean;
import com.epidemic.service.impl.GraphServiceImlp;
import com.epidemic.util.GraphHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class GraphController {

    @Autowired
    private GraphServiceImlp graphServiceImlp;

    @Autowired
    private DataService dataService;

//    折线图
    @GetMapping("/graph")
    public String graph(Model model) {

        List<GraphBean> list = graphServiceImlp.list();

        ArrayList<String> dateList = new ArrayList<>();
        ArrayList<Integer> confirmList = new ArrayList<>();
        ArrayList<Integer> healList = new ArrayList<>();
        ArrayList<Integer> deadList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            GraphBean bean = list.get(i);
            dateList.add(bean.getDate());
            confirmList.add(bean.getConfirm());
            healList.add(bean.getHeal());
            deadList.add(bean.getDead());
        }

        model.addAttribute("dateList", new Gson().toJson(dateList));
        model.addAttribute("confirmList", new Gson().toJson(confirmList));
        model.addAttribute("healList", new Gson().toJson(healList));
        model.addAttribute("deadList", new Gson().toJson(deadList));
        return "graph";
    }


//    柱状图
    @GetMapping("/graphBar")
    public String graphBar(Model model) {
        List<GraphBarBean> list = GraphHandler.getData();

        Collections.sort(list);

        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<Integer> fromAbroadList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            GraphBarBean bean = list.get(i);
            nameList.add(bean.getName());
            fromAbroadList.add(bean.getFromAbroad());
        }

        model.addAttribute("nameList", new Gson().toJson(nameList));
        model.addAttribute("fromAbroadList", new Gson().toJson(fromAbroadList));
        return "graphBar";
    }

//    地图
    @GetMapping("/map")
    public String map(Model model) {
        List<DataBean> list = dataService.list();

        List<MapBean> result1 = new ArrayList<>();
        List<MapBean> result2 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {

            DataBean dataBean = list.get(i);
            MapBean mapBean1 = new MapBean(dataBean.getName(),
                    dataBean.getNowConfirm());
            result1.add(mapBean1);

            MapBean mapBean2 = new MapBean(dataBean.getName(),
                    dataBean.getConfirm());
            result2.add(mapBean2);
        }

        model.addAttribute("mapData1", new Gson().toJson(result1));
        model.addAttribute("mapData2", new Gson().toJson(result2));
        return "map";
    }



}