package com.epidemic.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GraphBean {

    private String date;
    private int confirm;
    private int heal;
    private int dead;

}
