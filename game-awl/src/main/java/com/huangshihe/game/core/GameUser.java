package com.huangshihe.game.core;

/**
 * Created by Administrator on 2016/7/24.
 */
public interface GameUser {

    void setUserId(int userId);

    int getUserId();

    void setIdentity(Identity identity);

    Identity getIdentity();

    /**
     * 设置用户编号
     * @param num 游戏用户编号
     */
    void setNum(int num);

    int getNum();

    void setStatus(int status);

    int getStatus();

    /**
     * 设置游戏用户的特殊提示信息
     * @param info 游戏用户的特殊提示信息
     */
    void setInfo(String info);

    String getInfo();

    /**
     * 当前玩家正在游戏中
     */
    int LIVE = 1;

    /**
     * 当前玩家已结束游戏
     */
    int DEAD = 0;

    /**
     * 当前玩家处于未分配角色状态
     */
    int PREPARE = 2;
}

