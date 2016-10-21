package com.huangshihe.game.awl.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/7/26.
 */
public class Team {
    private int leaderNum;
    private List<AwlUser> members;
    private Date createTime;
    private int status;

    public Team(int leaderNum, AwlUser... awlUsers) {
        setLeaderNum(leaderNum);
        setMembers(awlUsers);
        setStatus(STATUS_ING);
        setCreateTime(new Date());
    }

    public Team(int leaderNum){
        setLeaderNum(leaderNum);
        setStatus(STATUS_ING);
        setCreateTime(new Date());
    }

    public int getLeaderNum() {
        return leaderNum;
    }

    public void setLeaderNum(int leaderNum) {
        this.leaderNum = leaderNum;
    }

    public List<AwlUser> getMembers() {
        if (members == null) {
            members = new ArrayList<AwlUser>();
        }
        return members;
    }

    public void setMembers(AwlUser... awlUsers) {
        if (awlUsers.length < 2 || awlUsers.length > 3) {
            throw new IllegalArgumentException("team member length not in [2,3]");
        }
        Arrays.stream(awlUsers).forEach(awlUser -> getMembers().add(awlUser));
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

//    /**
//     * 过期的队伍
//     */
//    public static final int STATUS_HISTORY = 1;

    // 失败和成功的队伍都属于过期的队伍
    /**
     * 组建失败的队伍
     */
    public static final int STATUS_FAIL = 2;

    /**
     * 组建成功的队伍
     */
    public static final int STATUS_SUCCESS = 3;

    /**
     * 创建中的队伍
     */
    public static final int STATUS_ING = 0;
}
