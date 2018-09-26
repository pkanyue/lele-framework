package com.rlax.lele.framework.component.charts.echarts.data;

import java.io.Serializable;

/**
 * 气泡图 data
 * @author Rlax
 *
 */
public class BubbleData implements Serializable {

    private String group;
    private String x;
    private String y;
    private String size;

    public BubbleData() {
    }

    public BubbleData(String group, String x, String y, String size) {
        this.group = group;
        this.x = x;
        this.y = y;
        this.size = size;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "BubbleData{" +
                "group='" + group + '\'' +
                ", x='" + x + '\'' +
                ", y='" + y + '\'' +
                ", size='" + size + '\'' +
                '}';
    }
}
