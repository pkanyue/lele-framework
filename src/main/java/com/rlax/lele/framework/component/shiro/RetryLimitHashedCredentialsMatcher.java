package com.rlax.lele.framework.component.shiro;

import com.rlax.lele.framework.consts.CacheKey;
import com.rlax.lele.framework.exception.BusinessException;
import io.jboot.Jboot;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 密码重试认证
 * @author Rlax
 *
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

	/** 允许密码重试最大次数 */
	private int allowRetryCount = 10;
	/** 账户将被锁定的时间 */
	private int lockedSeconds = 3600;

	@Override
	public boolean doCredentialsMatch(AuthenticationToken _token, AuthenticationInfo info) {
		MuitiLoginToken token = (MuitiLoginToken) _token;
		
		String username = (String) token.getPrincipal();
		AtomicInteger atomicInteger = Jboot.getCache().get(CacheKey.CACHE_SHIRO_PASSWORDRETRY, username);

		if (atomicInteger == null) {
			atomicInteger = new AtomicInteger(0);
		} else {
			atomicInteger.incrementAndGet();
		}
		Jboot.getCache().put(CacheKey.CACHE_SHIRO_PASSWORDRETRY, username, atomicInteger, lockedSeconds);

		if (atomicInteger.get() > allowRetryCount) {
			throw new ExcessiveAttemptsException();
		}

		boolean matches = false;
		
		if (token.getLoginType().equals(MuitiLoginToken.USERPASSWORD_MODE)) {
			matches = super.doCredentialsMatch(token, info);
		} else if (token.getLoginType().equals(MuitiLoginToken.TOKEN_MODE)) {
			SimpleCredentialsMatcher simpleMatcher = new SimpleCredentialsMatcher();
			matches = simpleMatcher.doCredentialsMatch(token, info);
		} else {
			throw new BusinessException("not support login type :" + token.getLoginType());
		}
		
		if (matches) {
			Jboot.getCache().remove(CacheKey.CACHE_SHIRO_PASSWORDRETRY, username);
		}
		
		return matches;
	}

	public int getAllowRetryCount() {
		return allowRetryCount;
	}

	public void setAllowRetryCount(int allowRetryCount) {
		this.allowRetryCount = allowRetryCount;
	}

	public int getLockedSeconds() {
		return lockedSeconds;
	}

	public void setLockedSeconds(int lockedSeconds) {
		this.lockedSeconds = lockedSeconds;
	}
}
