package com.epidemic.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class GraphBarBean implements Comparable<GraphBarBean> {

    private String name;
    private int fromAbroad;

    //     简单比较的工具
    @Override
    public int compareTo(GraphBarBean o) {
        return o.getFromAbroad() - this.getFromAbroad();
    }
}
