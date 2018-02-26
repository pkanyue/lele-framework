package com.rlax.lele.framework.component.oss.aliyun;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.jfinal.kit.PropKit;
import com.rlax.lele.framework.web.base.BaseController;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * aliyun aliyun 服务端签名后直传控制器
 * @author Rlax
 *
 */
public class PostObjectPolicyController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(PostObjectPolicyController.class);
	
	public void index() {
		logger.info("");
	    String endpoint = PropKit.get("aliyun.aliyun.endpoint");
        String accessId = PropKit.get("aliyun.aliyun.accessId");
        String accessKey = PropKit.get("aliyun.aliyun.accessKey");
        String bucket = PropKit.get("aliyun.aliyun.bucket");
        String dir = "user-dir";
        String host = "http://" + bucket + "." + endpoint;
		
        OSSClient client = new OSSClient(endpoint, accessId, accessKey);
        try {
	    	long expireTime = 30;
	    	long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
	        Date expiration = new Date(expireEndTime);
	        PolicyConditions policyConds = new PolicyConditions();
	        policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
	        policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
	
	        String postPolicy = client.generatePostPolicy(expiration, policyConds);
	        byte[] binaryData = postPolicy.getBytes("utf-8");
	
	        String encodedPolicy = BinaryUtil.toBase64String(binaryData);
	        String postSignature = client.calculatePostSignature(postPolicy);
	        
	        Map<String, String> respMap = new LinkedHashMap<String, String>();
	        respMap.put("accessid", accessId);
	        respMap.put("policy", encodedPolicy);
	        respMap.put("signature", postSignature);
	        //respMap.put("expire", formatISO8601Date(expiration));
	        respMap.put("dir", dir);
	        respMap.put("host", host);
	        respMap.put("expire", String.valueOf(expireEndTime / 1000));
	        JSONObject ja1 = JSONObject.fromObject(respMap);
	        
	        logger.info("返回 json {}", ja1.toString());
	        
	        getResponse().setHeader("Access-Control-Allow-Origin", "*");
	        getResponse().setHeader("Access-Control-Allow-Methods", "GET, POST");
	    
	        renderJson(ja1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			logger.error("aliyun 签名错误 {}", e.getMessage());
		}	
	}
	
	public void onExceptionError(Exception e) {
		e.printStackTrace();
		logger.error("aliyun 签名错误 {}", e.getMessage());
	}
}
