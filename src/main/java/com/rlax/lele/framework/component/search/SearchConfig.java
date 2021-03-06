package com.rlax.lele.framework.component.search;

import io.jboot.app.config.annotation.ConfigModel;

/**
 * Search config
 * @author Rlax
 *
 */
@ConfigModel(prefix = "lele.search")
public class SearchConfig {

    public static final String TYPE_ELASTICSEARCH = "type_elasticsearch";

    private String type = TYPE_ELASTICSEARCH;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
