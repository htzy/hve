package com.huangshihe.rt.awl.packet;

/**
 * Created by Administrator on 2016/8/14.
 */
@Deprecated
public class FullPacket{

    /**
     * 基本信息包
     */
    private BasePacket basePacket;

    /**
     * 队伍信息包
     */
    private TeamPacket teamPacket;

    /**
     * 投票信息包
     */
    private VotePacket votePacket;

    /**
     * 任务信息包
     */
    private TaskPacket taskPacket;

    public FullPacket(BasePacket basePacket, TeamPacket teamPacket){
        setBasePacket(basePacket);
        setTeamPacket(teamPacket);
    }

    public BasePacket getBasePacket() {
        return basePacket;
    }

    public void setBasePacket(BasePacket basePacket) {
        this.basePacket = basePacket;
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

    public TaskPacket getTaskPacket() {
        return taskPacket;
    }

    public void setTaskPacket(TaskPacket taskPacket) {
        this.taskPacket = taskPacket;
    }

    @Override
    public String toString() {
        return "FullPacket{" +
                "basePacket=" + getBasePacket() +
                ", teamPacket=" + getTeamPacket() +
                ", votePacket=" + getVotePacket() +
                ", taskPacket=" + getTaskPacket() +
                '}';
    }
}
