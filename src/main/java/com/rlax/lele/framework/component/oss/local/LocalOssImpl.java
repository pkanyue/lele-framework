package com.rlax.lele.framework.component.oss.local;

import com.rlax.lele.framework.component.oss.BaseOss;
import com.rlax.lele.framework.component.oss.FileInfo;
import com.rlax.lele.framework.component.oss.OssConfig;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * 本地存储实现类
 * @author Rlax
 *
 */
public class LocalOssImpl extends BaseOss {

    private OssConfig ossConfig;

    public LocalOssImpl(OssConfig ossConfig) {
        this.ossConfig = ossConfig;
    }

    @Override
    public boolean save(File file, String savePath) {
        return false;
    }

    @Override
    public boolean save(byte[] data, String savePath) {
        return false;
    }

    @Override
    public boolean save(InputStream is, String savePath) {
        return false;
    }

    @Override
    public List<FileInfo> list(String dirPath) {
        return null;
    }

    @Override
    public List<FileInfo> list() {
        return null;
    }

    @Override
    public String getDownloadEndpoint() {
        return null;
    }

    @Override
    public String getDownloadPath(String key) {
        return null;
    }

    @Override
    public String getOssAcl() {
        return null;
    }

    @Override
    public OssConfig getOssConfig() {
        return null;
    }
}
