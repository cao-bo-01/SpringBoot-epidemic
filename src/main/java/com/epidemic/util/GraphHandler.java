package com.epidemic.util;

import com.epidemic.bean.GraphBarBean;
import com.epidemic.bean.GraphBean;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Map;

//      这个类用来拆解数据 并且封装对象 给业务层提供数据支持    从请求的 响应中 ，获取数据，拆解为对象
public class GraphHandler {


    //    TODO  折线图
    public static ArrayList<GraphBean> getGraphData() {
        String urlStr = "https://api.inews.qq.com/newsqa/v1/query/inner/publish/modules/list?" +
                "modules=chinaDayList,chinaDayAddList,cityStatis,nowConfirmStatis,provinceCompare";

        String str = HttpConnUtil.doGet(urlStr);
        Gson gson = new Gson();
        Map map = gson.fromJson(str, Map.class);

        Map subMap = (Map) map.get("data");
        ArrayList list = (ArrayList) subMap.get("chinaDayList");

        ArrayList<GraphBean> rsult = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            Map tmp = (Map) list.get(i);

            String date = (String) tmp.get("date");
            double confirm = (double) tmp.get("confirm");
            double heal = (double) tmp.get("heal");
            double dead = (double) tmp.get("dead");

            GraphBean graphBean = new GraphBean();
            graphBean.setDead((int) dead);
            graphBean.setDate(date);
            graphBean.setConfirm((int) confirm);
            graphBean.setHeal((int) heal);

            rsult.add(graphBean);
        }

        return rsult;
    }


    //    TODO  条形图
    public static ArrayList<GraphBarBean> getData() {

        String url = "https://view.inews.qq.com/g2/getOnsInfo?name=disease_h5";

        String jsonString = HttpConnUtil.doGet(url);
        Gson gson = new Gson();

        Map map = gson.fromJson(jsonString, Map.class);

        String data = (String) map.get("data");
        Map subMap = gson.fromJson(data, Map.class);

        ArrayList areaList = (ArrayList) subMap.get("areaTree");
        Map dataMap = (Map) areaList.get(0);
        ArrayList childrenList = (ArrayList) dataMap.get("children");

        ArrayList<GraphBarBean> result = new ArrayList<>();

        for (int i = 0; i < childrenList.size(); i++) {
            Map tmp = (Map) childrenList.get(i);
            String name = (String) tmp.get("name");

            ArrayList children = (ArrayList) tmp.get("children");
            for (int j = 0; j < children.size(); j++) {
                Map subTmp = (Map) children.get(j);
                if ("境外输入".equals((String) subTmp.get("name"))) {
                    Map total = (Map) subTmp.get("total");
                    double fromAbroad = (Double) total.get("confirm");
                    GraphBarBean bean = new GraphBarBean(name, (int) fromAbroad);
                    result.add(bean);
                }

            }

        }
            return result;
        }





    }
