package com.huangshihe.game.awl.manage;

import com.huangshihe.game.awl.core.Awl;
import com.huangshihe.game.manage.GameManage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/23.
 */
public class AwlCache implements GameManage<Awl>{

    private static AwlCache instance;

    private AwlCache() {

    }

    private List<Awl> awls;

    public static GameManage<Awl> getInstance() {
        if(instance == null){
            instance = new AwlCache();
        }
        return instance;
    }

    @Override
    public List<Awl> getAll() {
        if (awls == null) {
            awls = new ArrayList<Awl>();
        }
        return awls;
    }

    @Override
    public Awl get(int id) {
        return getAll().stream().filter(game -> game.getId() == id).findFirst().orElse(null);
    }

    @Override
    public boolean create(int creatorId, String creatorName) {
        // 同一个用户不能同时创建多个游戏
        if(get(creatorId) == null){
            // 创建一个游戏
            Awl awl = new Awl(creatorId, creatorId, creatorName);
            return getAll().add(awl);
        }
        return false;
    }

    @Override
    public boolean remove(int id) {
        return getAll().remove(get(id));
    }
}
