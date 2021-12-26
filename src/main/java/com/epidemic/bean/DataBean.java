package com.epidemic.bean;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("dataa")     // @TableName("dataa") 对应数据库的名字
public class DataBean implements Serializable {

    private Long id;
    private String name;                //  地名
    private int nowConfirm;          //  现有确诊
    private int confirm;                //  累计确诊
    private int dead;                //  死亡人数
    private int heal;                //  治愈


}



