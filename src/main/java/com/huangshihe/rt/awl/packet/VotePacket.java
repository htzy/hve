package com.huangshihe.rt.awl.packet;

/**
 * Created by Administrator on 2016/8/14.
 */
public class VotePacket {
    private int awlUserNum;
    /**
     * 投票结果，是否同意
     */
    private boolean agree;

    public VotePacket() {
    }

    public VotePacket(int awlUserNum, boolean agree) {
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

    @Override
    public String toString() {
        return "VotePacket{" +
                "awlUserNum=" + awlUserNum +
                ", agree=" + agree +
                '}';
    }
}
