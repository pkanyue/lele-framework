package com.rlax.lele.framework.component.charts.echarts.data;

import java.io.Serializable;

/**
 * 折线图 data
 * @author Rlax
 *
 */
public class LineData implements Serializable {

    private String group;
    private String name;
    private String value;

    public LineData(String group, String name, String value) {
        this.group = group;
        this.name = name;
        this.value = value;
    }

    public LineData() {
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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "LineData{" +
                "group='" + group + '\'' +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
