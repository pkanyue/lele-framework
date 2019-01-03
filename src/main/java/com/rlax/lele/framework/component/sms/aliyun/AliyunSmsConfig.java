package com.rlax.lele.framework.component.sms.aliyun;

import com.jfinal.kit.StrKit;
import com.rlax.lele.framework.component.sms.SmsConfig;
import io.jboot.app.config.annotation.ConfigModel;

/**
 * 阿里云短信配置
 * @author Rlax
 *
 */
@ConfigModel(prefix = "lele.sms.aliyun")
public class AliyunSmsConfig extends SmsConfig {

    private String accessId;
    private String accessKey;

    public boolean isConfigOk() {
        return StrKit.notBlank(accessId) && StrKit.notBlank(accessKey);
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

}
