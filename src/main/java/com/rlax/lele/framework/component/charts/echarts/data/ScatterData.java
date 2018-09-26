package com.rlax.lele.framework.component.charts.echarts.data;

import java.io.Serializable;

/**
 * 散点图 data
 * @author Rlax
 *
 */
public class ScatterData implements Serializable {

    private String group;
    private String x;
    private String y;

    public ScatterData() {
    }

    public ScatterData(String group, String x, String y) {
        this.group = group;
        this.x = x;
        this.y = y;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "ScatterData{" +
                "group='" + group + '\'' +
                ", x='" + x + '\'' +
                ", y='" + y + '\'' +
                '}';
    }
}
