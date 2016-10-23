package com.huangshihe.game.awl.core;

/**
 * Created by Administrator on 2016/7/26.
 */
public class Vote {
    private int teamId;
    private int awlUserId;
    private boolean agree;

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getAwlUserId() {
        return awlUserId;
    }

    public void setAwlUserId(int awlUserId) {
        this.awlUserId = awlUserId;
    }

    public boolean isAgree() {
        return agree;
    }

    public void setAgree(boolean agree) {
        this.agree = agree;
    }
}
