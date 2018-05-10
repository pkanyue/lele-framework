package com.rlax.lele.framework.component.search;

import java.util.List;

/**
 * 搜索引擎
 * @author Rlax
 *
 */
public interface Search {

    /**
     * 创建文档
     * @param searchDocument
     * @return
     */
    public boolean create(SearchDocument searchDocument);

    /**
     * 批量创建文档
     * @param list
     * @return
     */
    public boolean createBatch(List<SearchDocument> list);

    /**
     * 修改文档
     * @param searchDocument
     * @return
     */
    public boolean update(SearchDocument searchDocument);


    /**
     * 删除文档
     * @param searchDocument
     * @return
     */
    public boolean delete(SearchDocument searchDocument);

    /**
     * 删除文档
     * @param index
     * @param type
     * @param id
     * @return
     */
    public boolean delete(String index, String type, String id);
}
