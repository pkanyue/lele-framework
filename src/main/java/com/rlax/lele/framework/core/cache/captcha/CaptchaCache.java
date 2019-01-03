package com.rlax.lele.framework.core.cache.captcha;

import com.jfinal.captcha.Captcha;
import com.jfinal.captcha.ICaptchaCache;
import com.rlax.lele.framework.consts.CacheKey;
import io.jboot.Jboot;

/**
 * 验证码 redis 集群
 * @author Rlax
 *
 */
public class CaptchaCache implements ICaptchaCache {

	@Override
	public void put(Captcha captcha) {
		Jboot.getCache().put(CacheKey.CACHE_CAPTCHAR_SESSION, captcha.getKey(), captcha, (int) (captcha.getExpireAt() - System.currentTimeMillis()) / 1000);
	}

	@Override
	public Captcha get(String key) {
		return Jboot.getCache().get(CacheKey.CACHE_CAPTCHAR_SESSION, key);
	}

	@Override
	public void remove(String key) {
		Jboot.getCache().remove(CacheKey.CACHE_CAPTCHAR_SESSION, key);
	}

	@Override
	public void removeAll() {
		Jboot.getCache().removeAll(CacheKey.CACHE_CAPTCHAR_SESSION);
	}

}
