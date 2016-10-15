package com.huangshihe.rt.awl.packet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/14.
 */
public class TeamPacket {
    private int creatorId;

    private List<Integer> members;

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
