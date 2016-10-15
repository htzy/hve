package com.huangshihe.game.core;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/7/23.
 */
public interface Game {

    int getId();

    void setId(int id);

    int getCreatorId();

    void setCreatorId(int creatorId);

    String getCreatorName();

    void setCreatorName(String creatorName);

    Date getCreateTime();

    void setCreateTime(Date createTime);

    List<GameUser> getGamers();

    boolean add(GameUser gameUser);

    boolean remove(GameUser gameUser);

    GameUser getGamer(int userId);

    int getRequireGamerNum();

    void setRequireGamerNum(int requireGamerNum);

    int getStatus();

    void setStatus(int status);

}
