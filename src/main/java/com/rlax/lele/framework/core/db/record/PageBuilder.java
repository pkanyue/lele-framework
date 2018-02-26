package com.rlax.lele.framework.core.db.record;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * 分页组装
 * @author Rlax
 *
 */
public class PageBuilder {

    /**
     * page record 转换为 page bean
     * @param page
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> Page<T> build(Page<Record> page, Class<T> tClass) {
        if (page == null) {
            return null;
        }

        List<T> list = RecordBuilder.build(page.getList(), tClass);
        return new Page<T>(list, page.getPageNumber(), page.getPageSize(), page.getTotalPage(), page.getTotalRow());
    }

}
