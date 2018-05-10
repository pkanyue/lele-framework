package com.rlax.lele.framework.component.search;

import com.jfinal.log.Log;
import com.rlax.lele.framework.component.search.elasticsearch.ElasticSearchImpl;
import io.jboot.Jboot;
import io.jboot.core.spi.JbootSpiLoader;

/**
 * Search manager
 * @author Rlax
 *
 */
public class SearchManager {

    private final static Log logger = Log.getLog(SearchManager.class);

    private static SearchManager me = new SearchManager();

    private SearchManager() {

    }

    private Search search;

    public static SearchManager me() {
        return me;
    }

    public Search getSearch() {
        if (search == null) {
            search = buildSearch();
        }
        return search;
    }

    private Search buildSearch() {
        SearchConfig searchConfig = Jboot.config(SearchConfig.class);

        switch (searchConfig.getType()) {
            case SearchConfig.TYPE_ELASTICSEARCH :
                return new ElasticSearchImpl();
            default:
                return JbootSpiLoader.load(Search.class, searchConfig.getType());
        }
    }
}
