package com.huangshihe.game.manage;

import java.util.List;

/**
 * Created by Administrator on 2016/7/24.
 */
public interface GameManage<T> {

    List<T> getAll();

    T get(int id);

    boolean create(int creatorId, String creatorName);

    boolean remove(int id);
}
