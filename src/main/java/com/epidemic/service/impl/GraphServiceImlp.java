package com.epidemic.service.impl;

import com.epidemic.bean.GraphBean;
import com.epidemic.util.GraphHandler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GraphServiceImlp {

    public ArrayList<GraphBean> list(){
       return  GraphHandler.getGraphData();
    }

}
