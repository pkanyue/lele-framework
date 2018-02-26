package com.rlax.lele.framework.component.sms;

import java.util.Map;
import java.util.Set;

/**
 * 短信接口
 * @author Rlax
 *
 */
public interface Sms {

    /**
     * 发送短信
     * @param phoneNo 手机号码
     * @param message 短信内容
     */
    public void send(String phoneNo, String message);

    /**
     * 批量发送短信
     * @param phoneNo 手机号码
     * @param message 短信内容
     */
    public void send(Set<String> phoneNo, String message);

    /**
     * 根据模版发送短信
     * @param phoneNo 接收号码
     * @param signName 签名名称
     * @param templateCode 模版编码
     * @param params 参数串
     */
    public void send(String phoneNo, String signName, String templateCode, Map<String, String> params);

    /**
     * 批量根据模版发送短信
     * @param phoneNoList 批量接收号码
     * @param signName 签名名称
     * @param templateCode 模版编码
     * @param params 参数串
     */
    public void send(Set<String> phoneNoList, String signName, String templateCode, Map<String, String> params);

}
