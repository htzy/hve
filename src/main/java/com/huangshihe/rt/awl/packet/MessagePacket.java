package com.huangshihe.rt.awl.packet;

/**
 * Created by Administrator on 2016/10/22.
 */
public class MessagePacket {
    // TODO use T
//    public <T> T getSessionAttr(String key) {
//        HttpSession session = request.getSession(false);
//        return session != null ? (T)session.getAttribute(key) : null;
//    }
    private String operate;
    private TeamPacket teamPacket;
    private VotePacket votePacket;

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public TeamPacket getTeamPacket() {
        return teamPacket;
    }

    public void setTeamPacket(TeamPacket teamPacket) {
        this.teamPacket = teamPacket;
    }

    public VotePacket getVotePacket() {
        return votePacket;
    }

    public void setVotePacket(VotePacket votePacket) {
        this.votePacket = votePacket;
    }

    @Override
    public String toString() {
        return "MessagePacket{" +
                "operate='" + operate + '\'' +
                ", teamPacket=" + teamPacket +
                ", votePacket=" + votePacket +
                '}';
    }
}
