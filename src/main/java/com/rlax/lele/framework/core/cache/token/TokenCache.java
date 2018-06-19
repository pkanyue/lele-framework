package com.rlax.lele.framework.core.cache.token;

import com.jfinal.token.ITokenCache;
import com.jfinal.token.Token;
import com.rlax.lele.framework.consts.CacheKey;
import io.jboot.Jboot;

import java.util.ArrayList;
import java.util.List;

/**
 * token cache
 * @author Rlax
 *
 */
public class TokenCache implements ITokenCache {

    private final static String CACHE_NAME = CacheKey.CACHE_TOKEN;

    @Override
    public void put(Token token) {
        Jboot.me().getCache().put(CACHE_NAME, token.getId(), token, (int) (token.getExpirationTime() - System.currentTimeMillis()) / 1000);
    }

    @Override
    public void remove(Token token) {
        Jboot.me().getCache().remove(CACHE_NAME, token.getId());
    }

    @Override
    public boolean contains(Token token) {
        return Jboot.me().getCache().get(CACHE_NAME, token.getId()) != null;
    }

    @Override
    public List<Token> getAll() {
        List keys = Jboot.me().getCache().getKeys(CACHE_NAME);

        List<Token> list = new ArrayList<>();
        for (Object key : keys) {
            Token token = Jboot.me().getCache().get(CACHE_NAME, key);
            if (token != null) {
                list.add(token);
            }
        }

        return list;
    }

}
