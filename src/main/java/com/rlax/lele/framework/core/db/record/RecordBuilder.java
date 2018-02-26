package com.rlax.lele.framework.core.db.record;

import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Record;

import java.util.ArrayList;
import java.util.List;

/**
 * Record 转 bean
 * @author Rlax
 *
 */
public class RecordBuilder {

    /**
     * Record转对象
     * @param record
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T build(Record record, Class<T> tClass) {
        if (record == null) {
            return null;
        }

        T t = null;
        try {
            t = JSON.parseObject(record.toJson(), tClass);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return t;
    }

    /**
     * 批量Record 转 dto
     * @param list
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> List<T> build(List<Record> list, Class<T> tClass) {
        if (list == null) {
            return null;
        }

        List<T> tList = new ArrayList<>();
        for (Record record : list) {
            tList.add(build(record, tClass));
        }
        return tList;
    }
}
