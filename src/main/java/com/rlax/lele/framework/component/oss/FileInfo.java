package com.rlax.lele.framework.component.oss;

import java.util.Date;

/**
 * 文件信息
 * @author Rlax
 *
 */
public class FileInfo {

	/** bucketName */
	private String bucketName;
	/** 相当于全路径 + 文件名 */
	private String key;
	/** 扩展名 */
	private String name;
	/** 扩展名 */
	private String ext;
	/** 文件大小 */
    private long size;
    /** 最后修改时间 */
    private Date lastModified;
    
	public String getBucketName() {
		return bucketName;
	}
	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public Date getLastModified() {
		return lastModified;
	}
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
}