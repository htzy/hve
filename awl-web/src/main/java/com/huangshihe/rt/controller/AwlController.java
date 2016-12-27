package com.huangshihe.rt.controller;

import com.huangshihe.game.awl.core.Awl;
import com.huangshihe.game.awl.manage.AwlCache;
import com.huangshihe.rt.interceptor.UserLoginInterceptor;
import com.huangshihe.rt.model.User;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

/**
 * Created by Administrator on 2016/7/23.
 */
@Before(UserLoginInterceptor.class)
public class AwlController extends Controller {

    public void index() {
        // 查询所有的游戏
        setAttr("awlList", AwlCache.getInstance().getAll());
        render("index.jsp");
    }

    public void ing() {
        int creatorId = getParaToInt(0);
        Awl awl = AwlCache.getInstance().get(creatorId);
        setSessionAttr("awl", awl);
        render("ing.jsp");
    }

    public void create() {
        User user = getSessionAttr("loginUser");
        AwlCache awlCache = (AwlCache) AwlCache.getInstance();
        if (awlCache.create(user.getId(), user.getUsername())) {
            // 处于等待状态，等待第5个人进入的时候，自动加载游戏，分配游戏角色，在此之前，处于等待状态的用户可以随意聊天。
            redirect("/game_awl/ing/" + user.getId());
        } else {
            setAttr("info", "创建游戏失败！");
            forwardAction("/game_awl/");
        }
    }

    public void quit() {
        User user = getSessionAttr("loginUser");
        try {
            Awl awl = getSessionAttr("awl");
            if (awl.getCreatorId() == user.getId()) {
                // 如果是创建者退出，则结束该游戏
                AwlCache.getInstance().remove(awl.getId());
            } else {
                // 如果是参与者退出，则去除该玩家
                awl.remove(awl.getGamer(user.getId()));
            }
        } catch (NullPointerException ignored) {

        }
        redirect("/game_awl/");
    }
}