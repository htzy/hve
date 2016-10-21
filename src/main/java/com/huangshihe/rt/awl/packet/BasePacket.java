package com.huangshihe.rt.awl.packet;

import com.huangshihe.game.awl.core.Awl;
import com.huangshihe.game.core.GameUser;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016/8/14.
 */
public class BasePacket {

    /**
     * 延迟次数
     * TODO 暂不使用延时
     */
    private int delayTimes;

    /**
     * 成功次数
     */
    private int successTimes;

    /**
     * 失败次数
     */
    private int failTimes;

    /**
     * 玩家id
     */
    private int awlUserId;

    /**
     * 游戏创建者id
     */
    private int creatorId;

    /**
     * 状态
     */
    private int status;

    /**
     * 玩家信息（等待中和进行中）
     */
    private List<UserPacket> userPackets;

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


    public BasePacket(Awl awl) {
        setCreatorId(awl.getCreatorId());
        setSuccessTimes(awl.getTotalTaskSuccessCount());
        setFailTimes(awl.getTotalTaskFailCount());
        setStatus(awl.getStatus());

//        for (GameUser gameUser : awl.getGamers()){
//            getUserPackets().add(new UserPacket(gameUser));
//        }
        getUserPackets().addAll(awl.getGamers().stream().map(UserPacket::new)
                .collect(Collectors.toList()));
        getUserPackets().sort(Comparator.comparing(UserPacket::getIdentityNum));
    }

    public BasePacket(GameUser gameUser, int status) {
        setStatus(status);
        getUserPackets().add(new UserPacket(gameUser));
    }

    public List<UserPacket> getUserPackets() {
        if (userPackets == null) {
            userPackets = new ArrayList<UserPacket>();
        }
        return userPackets;
    }

    public void setUserPackets(List<UserPacket> userPackets) {
        this.userPackets = userPackets;
    }

    public int getDelayTimes() {
        return delayTimes;
    }

    public void setDelayTimes(int delayTimes) {
        this.delayTimes = delayTimes;
    }

    public int getSuccessTimes() {
        return successTimes;
    }

    public void setSuccessTimes(int successTimes) {
        this.successTimes = successTimes;
    }

    public int getFailTimes() {
        return failTimes;
    }

    public void setFailTimes(int failTimes) {
        this.failTimes = failTimes;
    }

    public int getAwlUserId() {
        return awlUserId;
    }

    public void setAwlUserId(int awlUserId) {
        this.awlUserId = awlUserId;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
        return "BasePacket{" +
                "delayTimes=" + delayTimes +
                ", successTimes=" + successTimes +
                ", failTimes=" + failTimes +
                ", awlUserId=" + awlUserId +
                ", creatorId=" + creatorId +
                ", status=" + status +
                ", userPackets=" + userPackets +
                ", teamPacket=" + teamPacket +
                ", votePacket=" + votePacket +
                ", taskPacket=" + taskPacket +
                '}';
    }
}
