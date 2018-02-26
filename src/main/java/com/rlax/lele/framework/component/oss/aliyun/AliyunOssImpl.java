package com.rlax.lele.framework.component.oss.aliyun;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.AccessControlList;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.jfinal.log.Log;
import com.rlax.lele.framework.component.oss.BaseOss;
import com.rlax.lele.framework.component.oss.FileInfo;
import com.rlax.lele.framework.component.oss.OssConfig;
import org.apache.commons.io.FilenameUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * aliyun aliyun 实现
 * @author Rlax
 *
 */
public class AliyunOssImpl extends BaseOss {

    private final static Log log = Log.getLog(AliyunOssImpl.class);

    /** 授权下载链接失效时间 */
    private static final int EXPIRE_SECONDS = 3600;

    /** 阿里云OSS配置 */
    private OssConfig aliyunOssConfig;

    public AliyunOssImpl(OssConfig ossConfig) {
        this.aliyunOssConfig = ossConfig;
    }

    @Override
    public boolean save(File file, String savePath) {
        if (savePath.startsWith("/")) {
            savePath = savePath.substring(1);
        }

        boolean result = true;
        OSSClient client = null;
        try {
            client = new OSSClient(aliyunOssConfig.getEndpoint(), aliyunOssConfig.getAccessId(), aliyunOssConfig.getAccessKey());
            client.putObject(aliyunOssConfig.getBucket(), savePath, file);
        } catch (Exception e) {
            result = false;
            log.error("oss error", e);
        } finally {
            if (client != null) {
                client.shutdown();
            }
        }
        return result;
    }

    @Override
    public boolean save(byte[] data, String savePath) {
        if (savePath.startsWith("/")) {
            savePath = savePath.substring(1);
        }

        boolean result = true;
        OSSClient client = null;
        try {
            client = new OSSClient(aliyunOssConfig.getEndpoint(), aliyunOssConfig.getAccessId(), aliyunOssConfig.getAccessKey());
            client.putObject(aliyunOssConfig.getBucket(), savePath, new ByteArrayInputStream(data));
        } catch (Exception e) {
            result = false;
            log.error("oss error", e);
        } finally {
            if (client != null) {
                client.shutdown();
            }
        }
        return result;
    }

    @Override
    public boolean save(InputStream is, String savePath) {
        if (savePath.startsWith("/")) {
            savePath = savePath.substring(1);
        }

        boolean result = true;
        OSSClient client = null;
        try {
            client = new OSSClient(aliyunOssConfig.getEndpoint(), aliyunOssConfig.getAccessId(), aliyunOssConfig.getAccessKey());
            client.putObject(aliyunOssConfig.getBucket(), savePath, is);
        } catch (Exception e) {
            result = false;
            log.error("oss error", e);
        } finally {
            if (client != null) {
                client.shutdown();
            }
        }
        return result;
    }

    @Override
    public List<FileInfo> list(String dirPath) {
        List<FileInfo> resultList = new ArrayList<FileInfo>();

        OSSClient client = null;
        try {
            client = new OSSClient(aliyunOssConfig.getEndpoint(), aliyunOssConfig.getAccessId(), aliyunOssConfig.getAccessKey());
            ObjectListing objectListing = client.listObjects(aliyunOssConfig.getBucket(), dirPath);

            List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
            for (OSSObjectSummary s : sums) {
                FileInfo info = new FileInfo();
                info.setBucketName(s.getBucketName());
                info.setKey(s.getKey());
                info.setName(FilenameUtils.getBaseName(s.getKey()));
                info.setExt(FilenameUtils.getExtension(s.getKey()));
                info.setSize(s.getSize());
                info.setLastModified(s.getLastModified());

                resultList.add(info);
            }
        } catch (Exception e) {
            log.error("oss error", e);
        } finally {
            if (client != null) {
                client.shutdown();
            }
        }

        return resultList;
    }

    @Override
    public List<FileInfo> list() {
        List<FileInfo> resultList = new ArrayList<FileInfo>();

        OSSClient client = null;
        try {
            client = new OSSClient(aliyunOssConfig.getEndpoint(), aliyunOssConfig.getAccessId(), aliyunOssConfig.getAccessKey());
            ObjectListing objectListing = client.listObjects(aliyunOssConfig.getBucket());

            List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
            for (OSSObjectSummary s : sums) {
                FileInfo info = new FileInfo();
                info.setBucketName(s.getBucketName());
                info.setKey(s.getKey());
                info.setName(FilenameUtils.getBaseName(s.getKey()));
                info.setExt(FilenameUtils.getExtension(s.getKey()));
                info.setSize(s.getSize());
                info.setLastModified(s.getLastModified());

                resultList.add(info);
            }
        } catch (Exception e) {
            log.error("oss error", e);
        } finally {
            if (client != null) {
                client.shutdown();
            }
        }

        return resultList;
    }

    @Override
    public String getDownloadEndpoint() {
        return aliyunOssConfig.getDownloadEndpoint();
    }

    @Override
    public String getDownloadPath(String key) {
        if (key.startsWith("/")) {
            key = key.substring(1);
        }

        OSSClient client = new OSSClient(aliyunOssConfig.getEndpoint(), aliyunOssConfig.getAccessId(), aliyunOssConfig.getAccessKey());
        String downUrl = null;
        if (client.doesObjectExist(aliyunOssConfig.getBucket(), key)) {
            AccessControlList acl = client.getBucketAcl(aliyunOssConfig.getBucket());
            String grant = acl.getCannedACL().toString();
            // private、public-read、public-read-write
            if (grant.equals(CannedAccessControlList.Private.toString())) {
                downUrl = client.generatePresignedUrl(aliyunOssConfig.getBucket(), key, new Date(System.currentTimeMillis() + EXPIRE_SECONDS * 1000)).toExternalForm();
            } else {
                downUrl = getDownloadEndpoint() + "/" + key;
            }
        }

        return downUrl;
    }

    @Override
    public String getOssAcl() {
        OSSClient client = new OSSClient(aliyunOssConfig.getEndpoint(), aliyunOssConfig.getAccessId(), aliyunOssConfig.getAccessKey());
        AccessControlList acl = client.getBucketAcl(aliyunOssConfig.getBucket());
        return acl.getCannedACL().toString();
    }

    @Override
    public OssConfig getOssConfig() {
        return aliyunOssConfig;
    }
}
