package com.huangshihe.rt.controller;

import com.huangshihe.game.awl.core.Awl;
import com.huangshihe.game.awl.manage.AwlCache;
import com.huangshihe.rt.interceptor.UserLoginInterceptor;
import com.huangshihe.rt.model.User;
import com.huangshihe.rt.socket.AwlCoreSocket;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

        // 获得等待中的玩家们
        List<User> userList = new ArrayList<User>();
        userList.addAll(awl.getGamers().stream().map(gameUser -> User.getUser(gameUser.getUserId()))
                .collect(Collectors.toList()));
        setAttr("userList", userList);
        render("ing.jsp");
    }

    public void create() {
        User user = getSessionAttr("loginUser");
        AwlCache awlCache = (AwlCache) AwlCache.getInstance();
        if (awlCache.create(user.getId(), user.getUsername())) {
            // 处于等待状态，等待第5个人进入的时候，自动加载游戏，分配游戏角色，在此之前，处于等待状态的用户可以随意聊天。
//            setSessionAttr("awl", awlCache.get(user.getId()));
            redirect("/game_awl/ing/" + user.getId());
        } else {
            setAttr("info", "创建游戏失败！");
            forwardAction("/game_awl/");
        }
    }

    public void quit() {
        Awl awl = getSessionAttr("awl");
        User user = getSessionAttr("loginUser");
        if (awl.getCreatorId() == user.getId()) {
            // 如果是创建者退出，则结束该游戏
            AwlCache.getInstance().remove(awl.getId());
        } else {
            // 如果是参与者退出，则去除该玩家
            awl.remove(awl.getGamer(user.getId()));
        }
        redirect("/game_awl/");
    }
}