package com.rlax.lele.framework.component.search;

import java.io.Serializable;

/**
 * 搜索文档
 * @author Rlax
 *
 */
public class SearchDocument implements Serializable {

    public SearchDocument(String index, String type) {
        this.index = index;
        this.type = type;
    }

    public SearchDocument(String id, String index, String type) {
        this.id = id;
        this.index = index;
        this.type = type;
    }

    public SearchDocument(String id, String index, String type, Object source) {
        this.id = id;
        this.index = index;
        this.type = type;
        this.source = source;
    }

    /** id */
    private String id;
    /** 索引 */
    private String index;
    /** 类型 */
    private String type;

    private Object source;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getSource() {
        return source;
    }

    public void setSource(Object source) {
        this.source = source;
    }

}
