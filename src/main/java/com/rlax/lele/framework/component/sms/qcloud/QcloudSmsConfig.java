package com.rlax.lele.framework.component.sms.qcloud;

import com.rlax.lele.framework.component.sms.SmsConfig;
import io.jboot.app.config.annotation.ConfigModel;
import io.jboot.utils.StrUtil;

/**
 * 腾讯云短信配置
 * @author Rlax
 *
 */
@ConfigModel(prefix = "lele.sms.qcloud")
public class QcloudSmsConfig extends SmsConfig {

    private Integer appid;
    private String appkey;

    public boolean isConfigOk() {
        return StrUtil.isNotBlank(appid) && StrUtil.isNotBlank(appkey);
    }

    public Integer getAppid() {
        return appid;
    }

    public void setAppid(Integer appid) {
        this.appid = appid;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }
}
