package com.rlax.lele.framework.component.oss;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * 文件
 * @author Rlax
 *
 */
public interface Oss {

    /**
     * 文件上传
     * @param file
     * @param savePath
     * @return
     */
    public boolean save(File file, String savePath);

    /**
     * 保存二进制文件
     * @param data 图片二进制信息
     * @param savePath 保存路径
     * @return state 状态接口
     */
    public boolean save(byte[] data, String savePath);

    /**
     * 保存流文件
     * @param is 流
     * @param savePath 保存路径
     * @return state 状态接口
     */
    public boolean save(InputStream is, String savePath);

    /**
     * 获取前缀下的文件信息
     * @param dirPath 前缀
     * @return
     */
    public List<FileInfo> list(String dirPath);

    /**
     * 获取前缀下的文件信息
     * @return
     */
    public List<FileInfo> list();

    /**
     * 获取文件下载endpoint
     * @return
     */
    public String getDownloadEndpoint();

    /**
     * 根据文件key获取私有文件下载地址
     * @param key 存储文件key
     * @return
     */
    public String getDownloadPath(String key);

    /**
     * 获取oss的公开性
     * @return
     */
    public String getOssAcl();

    /**
     * 获取oss config
     * @return
     */
    public OssConfig getOssConfig();
}
