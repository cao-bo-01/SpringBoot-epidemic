package com.epidemic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.epidemic.bean.DataBean;
import com.epidemic.mapper.DataMapper;
import com.epidemic.service.DataService;
import com.epidemic.util.DataHandeler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class DataServiceImpl extends ServiceImpl<DataMapper,DataBean> implements DataService  {


    @Override
    public ArrayList<DataBean> list()  {

     return DataHandeler.selectData();
//     return DataHandeler.selectDataBack();
//        return null;
    }


}
