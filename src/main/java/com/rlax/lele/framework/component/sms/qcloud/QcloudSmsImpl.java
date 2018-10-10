package com.rlax.lele.framework.component.sms.qcloud;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.httpclient.HTTPException;
import com.rlax.lele.framework.component.sms.BaseSms;
import com.rlax.lele.framework.component.sms.SmsException;
import io.jboot.Jboot;
import io.jboot.utils.StrUtils;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 腾讯云短信实现
 * @author Rlax
 *
 */
public class QcloudSmsImpl extends BaseSms {

    private static final Logger logger = LoggerFactory.getLogger(QcloudSmsImpl.class);

    private QcloudSmsConfig qcloudSmsConfig;

    public QcloudSmsImpl () {
        QcloudSmsConfig qcloudSmsConfig = Jboot.config(QcloudSmsConfig.class);
        if (qcloudSmsConfig.isConfigOk()) {
            this.qcloudSmsConfig = qcloudSmsConfig;
        } else {
            throw new SmsException("can not get sms, please check your config");
        }
    }

    @Override
    public void send(String phoneNo, String message) {

    }

    @Override
    public void send(Set<String> phoneNo, String message) {

    }

    @Override
    public void send(String phoneNo, String signName, String templateCode, Map<String, String> params) {
        ArrayList<String> list = new ArrayList<>();
        if (params != null) {
            for (String param : params.values()) {
                list.add(param);
            }
        }

        try {
            SmsSingleSender ssender = new SmsSingleSender(qcloudSmsConfig.getAppid(), qcloudSmsConfig.getAppkey());
            ssender.sendWithParam("86", phoneNo, Integer.parseInt(templateCode), list, "", "", "");
            // 签名参数未提供或者为空时，会使用默认签名发送短信
        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        }
    }

    @Override
    public void send(Set<String> phoneNoList, String signName, String templateCode, Map<String, String> params) {

    }
}
