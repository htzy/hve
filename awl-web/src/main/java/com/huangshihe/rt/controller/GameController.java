package com.huangshihe.rt.controller;

import com.jfinal.core.Controller;

/**
 * Created by Administrator on 2016/7/21.
 */
public class GameController extends Controller {

    public void index(){
        render("index.jsp");
    }

    public void test(){
        render("awl/test.jsp");
    }
}