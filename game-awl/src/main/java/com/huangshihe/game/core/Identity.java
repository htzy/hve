package com.huangshihe.game.core;

/**
 * Created by Administrator on 2016/7/24.
 */
public interface Identity {

    /**
     * 获得身份id
     * @return
     */
    int getId();

    /**
     * 获得类别
     * @return
     */
    int getType();

    /**
     * 获得身份名
     *
     * @return
     */
    String getName();

    /**
     * 获得身份描述
     *
     * @return
     */
    String getDescription();
}
