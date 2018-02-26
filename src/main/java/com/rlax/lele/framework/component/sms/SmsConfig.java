package com.rlax.lele.framework.component.sms;

import io.jboot.config.annotation.PropertyConfig;

/**
 * aliyun config
 * @author Rlax
 *
 */
@PropertyConfig(prefix = "lele.sms")
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
