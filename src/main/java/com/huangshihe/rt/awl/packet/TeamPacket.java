package com.huangshihe.rt.awl.packet;

import com.huangshihe.game.awl.core.AwlUser;
import com.huangshihe.game.awl.core.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016/8/14.
 */
public class TeamPacket {

    private int creatorNum;

    private List<Integer> members;

    private int memberCount;

    private String info;

    private int status;

    // TODO now???
    private TaskPacket taskPacket;

    public TeamPacket() {

    }

    public TeamPacket(Team team) {
        if (team != null) {
            setCreatorNum(team.getLeaderNum());
            setMemberCount(team.getRuledMemberCount());
            getMembers().addAll(team.getMembers().stream().map(AwlUser::getNum).collect(Collectors.toList()));
            setInfo(team.getVoteResult());
            setStatus(team.getStatus());
        }
    }

    public int getCreatorNum() {
        return creatorNum;
    }

    public void setCreatorNum(int creatorNum) {
        this.creatorNum = creatorNum;
    }

    public List<Integer> getMembers() {
        if (members == null) {
            members = new ArrayList<Integer>();
        }
        return members;
    }

    public void setMembers(List<Integer> members) {
        this.members = members;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public TaskPacket getTaskPacket() {
        return taskPacket;
    }

    public void setTaskPacket(TaskPacket taskPacket) {
        this.taskPacket = taskPacket;
    }

    @Override
    public String toString() {
        return "TeamPacket{" +
                "creatorNum=" + creatorNum +
                ", members=" + members +
                ", memberCount=" + memberCount +
                ", info='" + info + '\'' +
                ", status=" + status +
                ", taskPacket=" + taskPacket +
                '}';
    }
}
