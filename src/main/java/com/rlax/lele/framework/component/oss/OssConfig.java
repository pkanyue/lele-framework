package com.rlax.lele.framework.component.oss;

import com.jfinal.kit.StrKit;

/**
 * aliyun config
 * @author Rlax
 *
 */
public class OssConfig {

    public static final String TYPE_ALIYUN_OSS = "aliyun_oss";
    public static final String TYPE_SYSTEM = "local";

    private String type = TYPE_SYSTEM;

    /** 接入点 */
    private String endpoint;
    private String accessId;
    private String accessKey;
    private String bucket;
    /** 下载接入点 */
    private String downloadEndpoint;

    /** 如果是本地存储时生效，文件存储根路径 */
    private String rootPath;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isConfigOk() {
        if (type.equals(TYPE_ALIYUN_OSS)) {
            return StrKit.notBlank(endpoint) && StrKit.notBlank(accessId) && StrKit.notBlank(accessKey) && StrKit.notBlank(bucket) && StrKit.notBlank(downloadEndpoint);
        } else if (type.equals(TYPE_SYSTEM)) {
            return StrKit.notBlank(bucket) && StrKit.notBlank(downloadEndpoint) && StrKit.notBlank(rootPath);
        } else {
            return false;
        }
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessId() {
        return accessId;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getDownloadEndpoint() {
        return downloadEndpoint;
    }

    public void setDownloadEndpoint(String downloadEndpoint) {
        this.downloadEndpoint = downloadEndpoint;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }
}
