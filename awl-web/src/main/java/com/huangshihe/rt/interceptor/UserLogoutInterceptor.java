package com.huangshihe.rt.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

/**
 * 如果用户已经登录，则直接访问主页面
 * Created by 黄诗鹤 on 2015/3/22.
 */

public class UserLogoutInterceptor implements Interceptor {

    public void intercept(Invocation inv) {
        if(inv.getController().getSessionAttr("loginUser") == null){
            inv.invoke();
        }else{
            inv.getController().redirect("/welcome");
        }
    }
}

