package com.epidemic.util;


import com.epidemic.bean.DataBean;
import com.epidemic.service.DataService;
import com.google.gson.Gson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Map;

//      这个类提供持久化数据  并不能给提供给页面数据 页面的数据是在数据库中查询到的
//@Component

public class DataHandeler {

    @Autowired
    private DataService dataService;


    //    数据来源于 腾讯新闻的新冠肺炎疫情最新动态         解析json
    public static ArrayList<DataBean> selectData() {

        ArrayList result = new ArrayList();
        String url = "https://view.inews.qq.com/g2/getOnsInfo?name=disease_h5";

        String jsonString = HttpConnUtil.doGet(url);

        if ("".equals(jsonString) || jsonString.length() == 0) {
            System.out.println("jsonString 等于空");
            return result;
        }

        ArrayList childrenList = DataGsonLisr.dataGsonList(jsonString);

        for (int i = 0; i < childrenList.size(); i++) {
            Map tmp = (Map) childrenList.get(i);
            String name = (String) tmp.get("name");
            Map totalMap = (Map) tmp.get("total");

//            private String name;                //  地名
//            private int nowConfirm;          //  现有确诊
//            private int confirm;                //  累计确诊
//            private int dead;                //  死亡人数
//            private int heal;                //  治愈

            double nowConfirm = (Double) totalMap.get("nowConfirm");
            double confirm = (Double) totalMap.get("confirm");
            double dead = (Double) totalMap.get("dead");
            double heal = (Double) totalMap.get("heal");

            DataBean dataBean = new DataBean();

            dataBean.setName(name);
            dataBean.setNowConfirm((int) nowConfirm);
            dataBean.setConfirm((int) confirm);
            dataBean.setHeal((int) heal);
            dataBean.setDead((int) dead);

            result.add(dataBean);
        }

        System.out.println(result);
        System.out.println("数据来自 腾讯新闻 ");
        return result;
    }


    //    数据来源于 丁香医生 全国新冠肺炎疫情地图             解析HTML
    public static ArrayList<DataBean> selectDataBack() {
        System.out.println("我是丁香医生");
        String url = "https://ncov.dxy.cn/ncovh5/view/pneumonia";

        ArrayList<DataBean> dataBeans = new ArrayList<>();
        String str = HttpConnUtil.doGet(url);
        if ("".equals(str) || str.length() == 0) {
            System.out.println("丁香医生的等于空 ");
            return dataBeans;
        }

        Document doc = Jsoup.parse(str);
        Element oneScript = doc.getElementById("getAreaStat");
        String data = oneScript.data();

//          TODO 经过测试 数据拿到了
//        System.out.println(data);

        String subData = data.substring(data.indexOf("["), data.lastIndexOf("]") + 1);

        ArrayList list = new Gson().fromJson(subData, ArrayList.class);

        for (int i = 0; i < list.size(); i++) {
            Map map = (Map) list.get(i);
            String name = (String) map.get("provinceName");
            double nowConfirm = (Double) map.get("currentConfirmedCount");
            double confirm = (Double) map.get("confirmedCount");
            double dead = (Double) map.get("deadCount");
            double heal = (Double) map.get("curedCount");

            DataBean dataBean = new DataBean();
            dataBean.setName(name);
            dataBean.setNowConfirm((int) nowConfirm);
            dataBean.setConfirm((int) confirm);
            dataBean.setDead((int) dead);
            dataBean.setHeal((int) heal);

            dataBeans.add(dataBean);
        }

//        System.out.println(dataBeans);
        System.out.println("数据来自 丁香医生 ");
        return dataBeans;
    }


    //    邮件发送      项目启动 执行一次
    @Autowired
    MailComponent mailComponent;

    //      项目初始化的时候 执行一次的代码 给数据库进行刷新
    @PostConstruct
    public void saveData() {
        String str = "";
        str = str + "项目启动日志，提示正常启动 \n 项目是EP纳新用，如果想要他用，请联系 \n 作者 ：曹博 \n 电话：13629101894";
        str = str + "初始化数据 \n";
        System.out.println("初始化数据");
        ArrayList<DataBean> arrayList = null;
//        先从腾讯新闻获取
        arrayList = selectData();
//        如果获取失败 从丁香医生获取
        if (arrayList == null || arrayList.size() == 0) {
            str = str + "数据从腾讯新闻获取失败 \n";
            System.out.println("teng xun xin wen shibai");
        } else {
            str = str + "数据来自腾讯新闻 \n";
            System.out.println("teng xun xin wen 成功");
            dataService.remove(null);
            dataService.saveBatch(arrayList);
            str = str + "数据初始化已完成 \n";
            mailComponent.send(str);
            return;
        }
        arrayList = selectDataBack();
        if (arrayList == null && arrayList.size() == 0) {
            str = str + "数据从丁香医生获取失败 \n";
            System.out.println("初始化失败，暂且使用旧数据");
            str = str + "初始化失败，暂且使用旧数据 \n";
        } else {
            str = str + "数据来自丁香医生 \n";
            dataService.remove(null);
            dataService.saveBatch(arrayList);
            str = str + "数据初始化已完成 \n";
            mailComponent.send(str);
        }
    }

    //    每30分钟刷新数据一次
    @Scheduled(cron = "0 0/30 * * * ? ")
    public void upData() {
        ArrayList<DataBean> arrayList = null;
        System.out.println("更新数据");

//        先从腾讯新闻获取
        arrayList = selectData();

//        如果获取失败 从丁香医生获取
        if (arrayList == null) {
            arrayList = selectDataBack();
        }
        if (arrayList != null) {
            dataService.remove(null);
            dataService.saveBatch(arrayList);
            System.out.println("数据自动更新成功");
        } else {
            System.out.println("数据自动更新失败");
        }


    }


}



