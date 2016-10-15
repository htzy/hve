package com.huangshihe.rt.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

/**
 * 如果用户没有登录，则访问登录界面
 * @author Sherlock
 *
 */
public class UserLoginInterceptor implements Interceptor {

    public void intercept(Invocation inv) {
        if(inv.getController().getSessionAttr("loginUser") == null){
            inv.getController().redirect("/login");
        }else{
            inv.invoke();
        }
    }
}