package com.huangshihe.rt.awl.packet;

import com.huangshihe.game.awl.core.Awl;
import com.huangshihe.game.awl.core.AwlIdentity;
import com.huangshihe.game.awl.core.Task;
import com.huangshihe.game.awl.core.Team;
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
     * 对于该awl对象的所有玩家生效的基本信息包
     *
     * @param awl
     */
    public BasePacket(Awl awl) {
        setCreatorId(awl.getCreatorId());
        setSuccessTimes(awl.getTotalTaskSuccessCount());
        setFailTimes(awl.getTotalTaskFailCount());
        setDelayTimes(awl.getDelayTimes());
        setStatus(awl.getStatus());
        Team team = awl.getCurrentTeam();
        if (team != null) {
            setTeamPacket(new TeamPacket(team));
        }
        getUserPackets().addAll(awl.getGamers().stream().map(UserPacket::new).collect(Collectors.toList()));
        getUserPackets().sort(Comparator.comparing(UserPacket::getIdentityNum));
    }

    public BasePacket(Awl awl, Team lastTeam) {
        setCreatorId(awl.getCreatorId());
        setSuccessTimes(awl.getTotalTaskSuccessCount());
        setFailTimes(awl.getTotalTaskFailCount());
        setDelayTimes(awl.getDelayTimes());
        setStatus(awl.getStatus());
        setTeamPacket(new TeamPacket(lastTeam));

        // 可能游戏已经结束
        if (awl.getStatus() == Awl.STATUS_ED && awl.getTotalTaskFailCount() >= 3) {
            // 坏人直接赢
            getUserPackets().addAll(awl.getGamers().stream()
                    .filter(gameUser -> gameUser.getIdentity().getType()== AwlIdentity.BAD_TYPE)
                    .map(UserPacket::new).collect(Collectors.toList()));
        } else if (awl.getStatus() == Awl.STATUS_ED && awl.getTotalTaskSuccessCount() >= 3) {
            // 添加刺客信息，并附上其余的好人信息让刺客选择
            UserPacket assassin = new UserPacket(awl.getGamers().stream()
                    .filter(gameUser -> gameUser.getIdentity().getId()== AwlIdentity.AwlIdentityEnum.ASSASSIN.ordinal())
                    .findFirst().orElse(null));
            getUserPackets().add(assassin);

            List<UserPacket> good = awl.getGamers().stream()
                    .filter(gameUser -> gameUser.getIdentity().getType() == AwlIdentity.GOOD_TYPE)
                    .map(UserPacket::new).collect(Collectors.toList());
            good.sort(Comparator.comparing(UserPacket::getIdentityNum));
            getUserPackets().addAll(good);
        } else {
            getUserPackets().addAll(awl.getGamers().stream().map(UserPacket::new).collect(Collectors.toList()));
            getUserPackets().sort(Comparator.comparing(UserPacket::getIdentityNum));
        }
    }

    /**
     * 对于该玩家生效的基本信息包
     *
     * @param gameUser
     * @param status
     */
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
                '}';
    }
}
