package com.rlax.lele.framework.component.search.elasticsearch;

import com.rlax.lele.framework.component.sms.SmsConfig;
import io.jboot.config.annotation.PropertyConfig;
import io.jboot.utils.StringUtils;

/**
 * elasticsearch 配置
 * @author Rlax
 *
 */
@PropertyConfig(prefix = "lele.search.elasticsearch")
public class ElasticSearchConfig extends SmsConfig {

    private String host;
    private int port = 9300;
    private String clusterName = "lele-es";
    /** 是否启动集群自动嗅探 */
    private boolean clientTransportSniff = false;
    /** 设置 true ，忽略连接节点集群名验证 */
    private boolean clientIgnoreClusterName = false;
    /** ping一个节点的响应时间 默认5秒 */
    private int clientTransportPingTimeout = 5;
    /** sample/ping 节点的时间间隔，默认是5s */
    private int clientTransportNodesSamplerInterval = 5;


    public boolean isConfigOk() {
        return StringUtils.isNotBlank(host);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isClientTransportSniff() {
        return clientTransportSniff;
    }

    public void setClientTransportSniff(boolean clientTransportSniff) {
        this.clientTransportSniff = clientTransportSniff;
    }

    public boolean isClientIgnoreClusterName() {
        return clientIgnoreClusterName;
    }

    public void setClientIgnoreClusterName(boolean clientIgnoreClusterName) {
        this.clientIgnoreClusterName = clientIgnoreClusterName;
    }

    public int getClientTransportPingTimeout() {
        return clientTransportPingTimeout;
    }

    public void setClientTransportPingTimeout(int clientTransportPingTimeout) {
        this.clientTransportPingTimeout = clientTransportPingTimeout;
    }

    public int getClientTransportNodesSamplerInterval() {
        return clientTransportNodesSamplerInterval;
    }

    public void setClientTransportNodesSamplerInterval(int clientTransportNodesSamplerInterval) {
        this.clientTransportNodesSamplerInterval = clientTransportNodesSamplerInterval;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }
}
