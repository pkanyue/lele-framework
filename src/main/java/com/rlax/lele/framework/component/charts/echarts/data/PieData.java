package com.rlax.lele.framework.component.charts.echarts.data;

import java.io.Serializable;

/**
 * 饼图 data
 * @author Rlax
 *
 */
public class PieData implements Serializable {

    private String name;
    private String value;

    public PieData(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public PieData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "PieData{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
