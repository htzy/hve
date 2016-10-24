package com.huangshihe.game.awl.core;

/**
 * Created by Administrator on 2016/7/26.
 */
public class Vote {
    private int awlUserNum;
    private boolean agree;

    public Vote() {
    }

    public Vote(int awlUserNum, boolean agree) {
        this.awlUserNum = awlUserNum;
        this.agree = agree;
    }

    public int getAwlUserNum() {
        return awlUserNum;
    }

    public void setAwlUserNum(int awlUserNum) {
        this.awlUserNum = awlUserNum;
    }

    public boolean isAgree() {
        return agree;
    }

    public void setAgree(boolean agree) {
        this.agree = agree;
    }
}
