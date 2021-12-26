package com.epidemic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.epidemic.bean.DataBean;
import org.mybatis.spring.annotation.MapperScan;

import java.util.ArrayList;


public interface DataService extends IService<DataBean>  {

    ArrayList<DataBean> list() ;

}
