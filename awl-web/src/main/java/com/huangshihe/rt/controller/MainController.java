package com.huangshihe.rt.controller;

import com.huangshihe.rt.interceptor.UserLoginInterceptor;
import com.huangshihe.rt.interceptor.UserLogoutInterceptor;
import com.huangshihe.rt.model.User;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.NoUrlPara;

/**
 * Created by root on 5/16/16.
 */
public class MainController extends Controller {

    //    @Before(value = {NoUrlPara.class})
    @Before(NoUrlPara.class)
    public void index() {
        render("index.jsp");
    }

    @Before(UserLogoutInterceptor.class)
    public void login() {
        render("login.jsp");
    }

    @Before(UserLoginInterceptor.class)
    public void logout() {
        removeSessionAttr("loginUser");
        redirect("/login");
    }

    @Before(UserLogoutInterceptor.class)
    public void checkLogin(){
        User toVerify = getModel(User.class);
        User user = User.verify(toVerify);
        if(user == null){
            // 登录失败
            redirect("/login");
        }else{
            setSessionAttr("loginUser", user);
            redirect("/welcome");
        }
    }

    @Before(UserLoginInterceptor.class)
    public void welcome(){
        render("welcome.jsp");
    }
}