package com.rlax.lele.framework.component.search.elasticsearch;

import com.jfinal.log.Log;
import com.rlax.lele.framework.component.search.BaseSearch;
import com.rlax.lele.framework.component.search.SearchDocument;
import com.rlax.lele.framework.component.search.SearchException;
import io.jboot.Jboot;
import io.jboot.utils.StringUtils;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * ElasticSearch 实现
 * @author Rlax
 *
 */
public class ElasticSearchImpl extends BaseSearch {

    private static final Log log = Log.getLog(ElasticSearchImpl.class);

    private TransportClient client;

    public ElasticSearchImpl() {
        ElasticSearchConfig elasticSearchConfig = Jboot.config(ElasticSearchConfig.class);
        if (!elasticSearchConfig.isConfigOk()) {
            throw new SearchException("can not get search, please check your config");
        }

        Settings esSettings = Settings.builder()
                //设置ES实例的名称
                .put("cluster.name", elasticSearchConfig.getClusterName())
                //自动嗅探整个集群的状态，把集群中其他ES节点的ip添加到本地的客户端列表中
                .put("client.transport.sniff", elasticSearchConfig.isClientTransportSniff())
                .build();

        //初始化client较老版本发生了变化，此方法有几个重载方法，初始化插件等。
        client = new PreBuiltTransportClient(esSettings);

        //此步骤添加IP，至少一个，其实一个就够了，因为添加了自动嗅探配置
        try {
            client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(elasticSearchConfig.getHost()), elasticSearchConfig.getPort()));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean create(SearchDocument searchDocument) {
        if (!StringUtils.areNotBlank(searchDocument.getIndex(), searchDocument.getType())) {
            throw new SearchException("create document must index and type not empty");
        }

        IndexRequestBuilder builder = client.prepareIndex(searchDocument.getIndex(), searchDocument.getType());

        if (StringUtils.isNotBlank(searchDocument.getId())) {
            builder.setId(searchDocument.getId());
        }

        IndexResponse response = builder.setSource(searchDocument.getSource()).get();
        return response.getResult().equals(DocWriteResponse.Result.CREATED);
    }

    @Override
    public boolean createBatch(List<SearchDocument> list) {
        BulkRequestBuilder bulkRequest = client.prepareBulk();

        for (SearchDocument searchDocument : list) {
            if (!StringUtils.areNotBlank(searchDocument.getIndex(), searchDocument.getType())) {
                throw new SearchException("create document must index and type not empty");
            }

            IndexRequestBuilder builder = client.prepareIndex(searchDocument.getIndex(), searchDocument.getType());
            if (StringUtils.isNotBlank(searchDocument.getId())) {
                builder.setId(searchDocument.getId());
            }
            builder.setSource(searchDocument.getSource());

            bulkRequest.add(builder);
        }

        BulkResponse bulkResponse = bulkRequest.get();
        return !bulkResponse.hasFailures();
    }

    @Override
    public boolean update(SearchDocument searchDocument) {
        if (!StringUtils.areNotBlank(searchDocument.getIndex(), searchDocument.getType(), searchDocument.getId())) {
            throw new SearchException("update document must index, type, id are not empty");
        }

        UpdateResponse response = client.prepareUpdate(searchDocument.getIndex(), searchDocument.getType(), searchDocument.getId())
                .setDoc(searchDocument.getSource())
                .get();

        return response.getResult().equals(DocWriteResponse.Result.UPDATED);
    }

    @Override
    public boolean delete(SearchDocument searchDocument) {
        if (!StringUtils.areNotBlank(searchDocument.getIndex(), searchDocument.getType(), searchDocument.getId())) {
            throw new SearchException("delete document must index, type, id are not empty");
        }

        DeleteResponse response = client.prepareDelete(searchDocument.getIndex(), searchDocument.getType(), searchDocument.getId()).get();
        return response.getResult().equals(DocWriteResponse.Result.DELETED);
    }

    @Override
    public boolean delete(String index, String type, String id) {
        return delete(new SearchDocument(index, type, id));
    }
}
