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

    public TeamPacket(Team team) {
        if (team != null) {
            setCreatorNum(team.getLeaderNum());
            setMemberCount(team.getMemberCount());
            getMembers().addAll(team.getMembers().stream().map(AwlUser::getNum).collect(Collectors.toList()));
        }
    }

    public TeamPacket(int creatorNum, List<Integer> members) {
        setCreatorNum(creatorNum);
        setMembers(members);
        setMemberCount(Team.memberCount[creatorNum]);
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
        if (members == null) {
            members = new ArrayList<Integer>();
        }
        this.members = members;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    @Override
    public String toString() {
        return "TeamPacket{" +
                "creatorNum=" + creatorNum +
                ", members=" + members +
                ", memberCount=" + memberCount +
                '}';
    }
}
