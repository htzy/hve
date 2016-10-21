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

    // TODO change id to num
    private int creatorId;

    private List<Integer> members;

    public TeamPacket(Team team) {
        if (team != null) {
            setCreatorId(team.getLeaderNum());
            getMembers().addAll(team.getMembers().stream().map(AwlUser::getNum).collect(Collectors.toList()));
        }
    }

    public TeamPacket(int creatorId, List<Integer> members) {
        setCreatorId(creatorId);
        setMembers(members);
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
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

    @Override
    public String toString() {
        return "TeamPacket{" +
                "creatorId=" + getCreatorId() +
                ", members=" + getMembers() +
                '}';
    }
}
