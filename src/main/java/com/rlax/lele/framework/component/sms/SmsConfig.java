package com.rlax.lele.framework.component.sms;

import io.jboot.app.config.annotation.ConfigModel;

/**
 * aliyun config
 * @author Rlax
 *
 */
@ConfigModel(prefix = "lele.sms")
public class SmsConfig {

    public static final String TYPE_ALIYUN_SMS = "aliyun_sms";

    private String type = TYPE_ALIYUN_SMS;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
