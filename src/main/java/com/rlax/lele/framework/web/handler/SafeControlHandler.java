package com.rlax.lele.framework.web.handler;

import com.jfinal.handler.Handler;
import com.jfinal.kit.HandlerKit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 不让访问直接模板，防止下载。不让让访问jsp，防止jsp木马
 * @author Rlax
 *
 */
public class SafeControlHandler extends Handler {

    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        if (target.startsWith("/template") && target.endsWith(".html")) {
            HandlerKit.renderError404(request, response, isHandled);
            return;
        }

        if (target.toLowerCase().endsWith(".jsp")) {
            HandlerKit.renderError404(request, response, isHandled);
            return;
        }


        next.handle(target, request, response, isHandled);
    }
}
