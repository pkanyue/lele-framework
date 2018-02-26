package com.rlax.lele.framework.component.sms;

import com.jfinal.log.Log;
import com.rlax.lele.framework.component.sms.aliyun.AliyunSmsImpl;
import io.jboot.Jboot;
import io.jboot.core.spi.JbootSpiLoader;

/**
 * aliyun sms manager
 * @author Rlax
 *
 */
public class SmsManager {

    private final static Log logger = Log.getLog(SmsManager.class);

    private static SmsManager me = new SmsManager();

    private SmsManager() {

    }

    private Sms sms;

    public static SmsManager me() {
        return me;
    }

    public Sms getSms() {
        if (sms == null) {
            sms = buildSms();
        }
        return sms;
    }

    private Sms buildSms() {
        SmsConfig smsConfig = Jboot.config(SmsConfig.class);

        switch (smsConfig.getType()) {
            case SmsConfig.TYPE_ALIYUN_SMS :
                return new AliyunSmsImpl();
            default:
                return JbootSpiLoader.load(Sms.class, smsConfig.getType());
        }
    }
}
