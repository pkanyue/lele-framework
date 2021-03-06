package com.rlax.lele.framework.codegen.model;


import io.jboot.app.config.annotation.ConfigModel;

/**
 * model代码生成配置
 * @author Rlax
 *
 */
@ConfigModel(prefix="lele.ge.model")
public class AppModelGeneratorConfig {

    /** entity 包名 */
    private String modelpackage;
    /** entity 包名 */
    private String dtopackage;
    /** status 包名 */
    private String statuspackage;
    /** 移除的表前缀 */
    private String removedtablenameprefixes;
    /** 不包含表 */
    private String excludedtable;
    /** 不包含表前缀 */
    private String excludedtableprefixes;

    public String getModelpackage() {
        return modelpackage;
    }

    public void setModelpackage(String modelpackage) {
        this.modelpackage = modelpackage;
    }

    public String getRemovedtablenameprefixes() {
        return removedtablenameprefixes;
    }

    public void setRemovedtablenameprefixes(String removedtablenameprefixes) {
        this.removedtablenameprefixes = removedtablenameprefixes;
    }

    public String getExcludedtable() {
        return excludedtable;
    }

    public void setExcludedtable(String excludedtable) {
        this.excludedtable = excludedtable;
    }

    public String getExcludedtableprefixes() {
        return excludedtableprefixes;
    }

    public void setExcludedtableprefixes(String excludedtableprefixes) {
        this.excludedtableprefixes = excludedtableprefixes;
    }

    public String getDtopackage() {
        return dtopackage;
    }

    public void setDtopackage(String dtopackage) {
        this.dtopackage = dtopackage;
    }

    public String getStatuspackage() {
        return statuspackage;
    }

    public void setStatuspackage(String statuspackage) {
        this.statuspackage = statuspackage;
    }

    @Override
    public String toString() {
        return "AppModelGeneratorConfig{" +
                "modelpackage='" + modelpackage + '\'' +
                ", dtopackage='" + dtopackage + '\'' +
                ", statuspackage='" + statuspackage + '\'' +
                ", removedtablenameprefixes='" + removedtablenameprefixes + '\'' +
                ", excludedtable='" + excludedtable + '\'' +
                ", excludedtableprefixes='" + excludedtableprefixes + '\'' +
                '}';
    }
}
