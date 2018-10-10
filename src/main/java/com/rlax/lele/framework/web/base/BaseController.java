package com.rlax.lele.framework.web.base;

import com.jfinal.core.NotAction;
import io.jboot.web.controller.JbootController;

/**
 * 控制器基类
 * @author Rlax
 *
 */
public class BaseController extends JbootController {

    private final static String UPMS_USER_ID = "uid";

    @NotAction
    public String getJwtLoginUid() {
        return getJwtPara(UPMS_USER_ID);
    }

}
