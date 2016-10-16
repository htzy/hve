package com.huangshihe.game.awl.core;

import com.huangshihe.game.core.GameUser;
import com.huangshihe.game.core.Identity;

/**
 * Created by Administrator on 2016/7/24.
 */
public class AwlUser implements GameUser{

    private int userId;
    private AwlIdentity awlIdentity;
    private int num;
    private int status;
    private String info;

    //  创建一个awl游戏者，需要用户id，同时初始化status，其中身份，num和info随后待游戏开局后分配
    public AwlUser(int userId){
        this.userId = userId;
        this.status = PREPARE;
    }

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public void setIdentity(Identity identity) {
        awlIdentity = (AwlIdentity) identity;
    }

    @Override
    public Identity getIdentity() {
        return awlIdentity;
    }

    @Override
    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public int getNum() {
        return num;
    }

    @Override
    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String getInfo() {
        return info;
    }

    /**
     * 空用户
     */
    public static final int EMPTY_AWL_USER = -1;
}
