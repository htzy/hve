package com.huangshihe.game.awl.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016/7/26.
 */
public class Team {
    private int leaderNum;
    private List<AwlUser> members;
    private Date createTime;
    private int status;
    private List<Vote> votes;
    private Task task;

    public Team(int leaderNum, AwlUser... awlUsers) {
        setLeaderNum(leaderNum);
        setMembers(awlUsers);
        setStatus(STATUS_ING);
        setCreateTime(new Date());
    }

    public Team(int leaderNum) {
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
        if (awlUsers.length != getRuledMemberCount()) {
            throw new IllegalArgumentException("team member length is't " + getRuledMemberCount());
        }
        Arrays.stream(awlUsers).forEach(awlUser -> getMembers().add(awlUser));
    }

    public boolean addMember(AwlUser awlUser) {
        if (getMembers().size() < getRuledMemberCount()) {
            return getMembers().add(awlUser);
        }
        return false;
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

    public int getRuledMemberCount() {
        return ruledTeamMemberCount[leaderNum];
    }

    public List<Vote> getVotes() {
        if (votes == null) {
            votes = new ArrayList<Vote>();
        }
        return votes;
    }

    public boolean addVote(int awlUserNum, boolean answer) {
        // 如果以前投过票，则不允许再次投票
        if (getVotes().stream().filter(vote1 -> vote1.getAwlUserNum() == awlUserNum).count() != 0) {
            return false;
        }
        return getVotes().add(new Vote(awlUserNum,answer));
    }

    public List<Vote> getDisAgreeVotes() {
        return getVotes().stream().filter(vote -> !vote.isAgree()).collect(Collectors.toList());
    }

    public List<Vote> getAgreeVotes() {
        return getVotes().stream().filter(Vote::isAgree).collect(Collectors.toList());
    }

    public String getVoteResult() {
        StringBuilder builder = new StringBuilder();
        if (getVotes().size() == 5) {
            builder.append("投票已完成！队长：");
            builder.append(getLeaderNum());
            List<Vote> agrees = getAgreeVotes();
            List<Vote> disAgrees = getDisAgreeVotes();
            if (agrees.size() > disAgrees.size()) {
                setStatus(STATUS_SUCCESS);
                setTask(new Task());
                builder.append("，组队成功！赞成的人有：");
                builder.append(agrees.stream().map(Vote::getAwlUserNum).sorted().collect(Collectors.toList()));
            } else {
                setStatus(STATUS_FAIL);
                builder.append("，组队失败！反对的人有：");
                builder.append(disAgrees.stream().map(Vote::getAwlUserNum).sorted().collect(Collectors.toList()));
            }
        }
        return builder.toString();
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    // 失败和成功的队伍都属于已创建完成的队伍
    /**
     * 组建队伍：失败
     */
    public static final int STATUS_FAIL = -1;

    /**
     * 组建队伍：成功
     */
    public static final int STATUS_SUCCESS = 1;

    /**
     * 创建中的队伍
     */
    public static final int STATUS_ING = 0;

    /**
     * 规定的队伍成员人数
     */
    private static int ruledTeamMemberCount[] = {2, 3, 2, 3, 3};
}
