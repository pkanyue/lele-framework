package com.rlax.lele.framework.component.oss.local;

import cn.hutool.core.io.FileUtil;
import com.jfinal.log.Log;
import com.rlax.lele.framework.component.oss.BaseOss;
import com.rlax.lele.framework.component.oss.FileInfo;
import com.rlax.lele.framework.component.oss.OssConfig;
import io.jboot.utils.FileUtils;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 本地存储实现类
 * @author Rlax
 *
 */
public class LocalOssImpl extends BaseOss {

    private final static Log log = Log.getLog(LocalOssImpl.class);

    /** 本地存储配置 */
    private OssConfig ossConfig;

    public LocalOssImpl(OssConfig ossConfig) {
        this.ossConfig = ossConfig;
    }

    private String getSavePath(String savePath) {
        return ossConfig.getRootPath() + FileUtil.getLineSeparator() + ossConfig.getBucket() + FileUtil.getLineSeparator() + savePath;
    }

    @Override
    public boolean save(File file, String savePath) {
        if (savePath.startsWith("/")) {
            savePath = savePath.substring(1);
        }

        boolean result = true;
        try {
            FileUtil.writeFromStream(FileUtil.getInputStream(file), getSavePath(savePath));
        } catch (Exception e) {
            result = false;
            log.error("oss error", e);
        }
        return result;
    }

    @Override
    public boolean save(byte[] data, String savePath) {
        if (savePath.startsWith("/")) {
            savePath = savePath.substring(1);
        }

        boolean result = true;
        try {
            FileUtil.writeBytes(data, getSavePath(savePath));
        } catch (Exception e) {
            result = false;
            log.error("oss error", e);
        }
        return result;
    }

    @Override
    public boolean save(InputStream is, String savePath) {
        if (savePath.startsWith("/")) {
            savePath = savePath.substring(1);
        }

        boolean result = true;
        try {
            FileUtil.writeFromStream(is, getSavePath(savePath));
        } catch (Exception e) {
            result = false;
            log.error("oss error", e);
        }
        return result;
    }

    @Override
    public List<FileInfo> list(String dirPath) {
        File[] files = FileUtil.ls(dirPath);

        List<FileInfo> resultList = new ArrayList<FileInfo>();
        for (File file : files) {
            FileInfo info = new FileInfo();
            info.setBucketName(ossConfig.getBucket());
            info.setKey(FileUtil.subPath(ossConfig.getRootPath() + FileUtil.getLineSeparator() + ossConfig.getBucket(), file.getAbsolutePath()));
            info.setName(FileUtil.mainName(file));
            info.setExt(FileUtil.extName(file));
            info.setSize(file.length());
            info.setLastModified(new Date(file.lastModified()));

            resultList.add(info);
        }
        return resultList;
    }

    @Override
    public List<FileInfo> list() {
        return null;
    }

    @Override
    public String getDownloadEndpoint() {
        return ossConfig.getDownloadEndpoint();
    }

    @Override
    public String getDownloadPath(String key) {
        if (key.startsWith("/")) {
            key = key.substring(1);
        }
        String downUrl = getDownloadEndpoint() + "/" + key;

        return null;
    }

    @Override
    public String getOssAcl() {
        return null;
    }

    @Override
    public OssConfig getOssConfig() {
        return ossConfig;
    }
}
