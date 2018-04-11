package com.rlax.lele.framework.codegen.model;


import io.jboot.config.annotation.PropertyConfig;

/**
 * model代码生成配置
 * @author Rlax
 *
 */
@PropertyConfig(prefix="lele.ge.model")
public class AppModelGeneratorConfig {

    /** entity 包名 */
    private String modelpackage;
    /** entity 包名 */
    private String dtopackage;
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

    @Override
    public String toString() {
        return "AppModelGeneratorConfig{" +
                "modelpackage='" + modelpackage + '\'' +
                ", dtopackage='" + dtopackage + '\'' +
                ", removedtablenameprefixes='" + removedtablenameprefixes + '\'' +
                ", excludedtable='" + excludedtable + '\'' +
                ", excludedtableprefixes='" + excludedtableprefixes + '\'' +
                '}';
    }
}
