package com.rlax.lele.framework.component.sms.aliyun;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.rlax.lele.framework.component.sms.BaseSms;
import com.rlax.lele.framework.component.sms.SmsException;
import io.jboot.Jboot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 阿里云短信
 * @author Rlax
 *
 */
public class AliyunSmsImpl extends BaseSms {

    private static final Logger logger = LoggerFactory.getLogger(AliyunSmsImpl.class);

    //产品名称:云通信短信API产品,开发者无需替换
    private static final String PRODUCT = "Dysmsapi";
    //产品域名,开发者无需替换
    private static final String DOMAIN = "dysmsapi.aliyuncs.com";

    private AliyunSmsConfig aliyunSmsConfig;

    public AliyunSmsImpl() {
        AliyunSmsConfig aliyunSmsConfig = Jboot.config(AliyunSmsConfig.class);
        if (aliyunSmsConfig.isConfigOk()) {
            this.aliyunSmsConfig = aliyunSmsConfig;
        } else {
            throw new SmsException("can not get sms, please check your config");
        }
    }

    @Override
    public void send(String phoneNo, String message) {
        throw new SmsException("not support");
    }

    @Override
    public void send(Set<String> phoneNo, String message) {
        throw new SmsException("not support");
    }

    @Override
    public void send(String phoneNo, String signName, String templateCode, Map<String, String> params) {
        Set<String> phoneNoList = new HashSet<>();
        phoneNoList.add(phoneNo);
        send(phoneNoList, signName, templateCode, params);
    }

    @Override
    public void send(Set<String> phoneNoList, String signName, String templateCode, Map<String, String> params) {
        String paramString = JSON.toJSONString(params);
        if (phoneNoList == null || phoneNoList.size() == 0) {
            logger.error("发送短信号码为空：签名{}, 模版编码{}, 参数串{}", new String[]{signName, templateCode, paramString});
            return;
        }

        StringBuilder phoneBuf = new StringBuilder();
        for (String phoneNo : phoneNoList) {
            phoneBuf.append(phoneNo).append(",");
        }
        String phoneNos = phoneBuf.substring(0, phoneBuf.length() - 1);

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        try {
            //初始化acsClient,暂不支持region化
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", aliyunSmsConfig.getAccessId(), aliyunSmsConfig.getAccessKey());
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", PRODUCT, DOMAIN);
            IAcsClient acsClient = new DefaultAcsClient(profile);

            //组装请求对象-具体描述见控制台-文档部分内容
            SendSmsRequest request = new SendSmsRequest();
            //必填:待发送手机号
            request.setPhoneNumbers(phoneNos);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName(signName);
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(templateCode);
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            request.setTemplateParam(JSON.toJSONString(params));

            //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
            //request.setSmsUpExtendCode("90997");

            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
            //request.setOutId("yourOutId");

            //hint 此处可能会抛出异常，注意catch
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            logger.info("发送短信：接收号码{}, 签名{}, 模版编码{}, 参数串{}", new String[]{phoneNos, signName, templateCode, paramString});
            logger.info("发送短信返回：MessageId{}, MessageMD5{}", new String[]{sendSmsResponse.getCode(), sendSmsResponse.getMessage()});
        } catch (ClientException e) {
            logger.error("发送短信异常：code {}, message {}", new String[]{e.getErrCode(), e.getErrMsg()});
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
