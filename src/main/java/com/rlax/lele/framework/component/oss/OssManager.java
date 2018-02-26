package com.rlax.lele.framework.component.oss;

import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;
import com.rlax.lele.framework.component.oss.aliyun.AliyunOssImpl;
import com.rlax.lele.framework.component.oss.local.LocalOssImpl;
import io.jboot.Jboot;
import io.jboot.core.spi.JbootSpiLoader;

import java.util.concurrent.ConcurrentHashMap;

/**
 * aliyun manager
 * @author Rlax
 *
 */
public class OssManager {

    private final static Log logger = Log.getLog(OssManager.class);

    private static OssManager me = new OssManager();

    private OssManager() {

    }

    private static final ConcurrentHashMap<String, Oss> map = new ConcurrentHashMap<String, Oss>();

    public static OssManager me() {
        return me;
    }

    public Oss getOss(String name) {
        if (StrKit.isBlank(name)) {
            logger.error("oss config name is null");
            return null;
        }

        Oss oss = map.get(name);
        if (oss == null) {
            oss = buildOss(name);
            if (oss != null) {
                map.put(name, oss);
            }
        }
        return oss;
    }

    private Oss buildOss(String name) {
        OssConfig ossConfig = Jboot.config(OssConfig.class, "lele.oss." + name);

        if (ossConfig == null || !ossConfig.isConfigOk()) {
            logger.error("oss config error");
            throw new OssException("oss config error");
        }

        switch (ossConfig.getType()) {
            case OssConfig.TYPE_ALIYUN_OSS :
                return new AliyunOssImpl(ossConfig);
            case OssConfig.TYPE_SYSTEM :
                return new LocalOssImpl(ossConfig);
            default:
                return JbootSpiLoader.load(Oss.class, ossConfig.getType());
        }
    }
}
