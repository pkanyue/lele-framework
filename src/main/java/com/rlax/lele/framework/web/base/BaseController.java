package com.rlax.lele.framework.web.base;

import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.NotAction;
import io.jboot.web.controller.JbootController;

/**
 * 控制器基类
 * @author Rlax
 *
 */
public class BaseController extends JbootController {

    private final static String UPMS_USER_ID = "uid";

    @Before(NotAction.class)
    public String getJwtLoginUid() {
        return getJwtPara(UPMS_USER_ID);
    }

}
