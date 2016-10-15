package com.huangshihe.rt.awl.packet;

/**
 * Created by Administrator on 2016/8/14.
 */
public class VotePacket {
    private int awlUserId;
    /**
     * 投票结果，是否同意
     */
    private boolean agree;

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

    @Override
    public String toString() {
        return "VotePacket{" +
                "awlUserId=" + getAwlUserId() +
                ", agree=" + isAgree() +
                '}';
    }
}
