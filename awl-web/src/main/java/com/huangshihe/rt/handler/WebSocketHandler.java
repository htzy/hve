package com.huangshihe.rt.handler;

import com.jfinal.handler.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2016/7/22.
 */
public class WebSocketHandler extends Handler {
    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        //对于websocket 不交予 jfinal 处理
        if (!target.contains("/ws")) {
            next.handle(target, request, response, isHandled);
        }
    }
}