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
    private StringBuilder info;

    public Team(int leaderNum, AwlUser... awlUsers) {
        setLeaderNum(leaderNum);
        setMembers(awlUsers);
        setStatus(STATUS_CREATING);
        setCreateTime(new Date());
        info = new StringBuilder();
    }

    public Team(int leaderNum) {
        setLeaderNum(leaderNum);
        setStatus(STATUS_CREATING);
        setCreateTime(new Date());
        info = new StringBuilder();
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
        return getVotes().add(new Vote(awlUserNum, answer));
    }

    public List<Vote> getDisAgreeVotes() {
        return getVotes().stream().filter(vote -> !vote.isAgree()).collect(Collectors.toList());
    }

    public List<Vote> getAgreeVotes() {
        return getVotes().stream().filter(Vote::isAgree).collect(Collectors.toList());
    }

    public boolean isFinishVoted() {
        return getVotes().size() == 5;
    }

    public String getVoteResult() {
        // team的信息info只需要生成一次即可
        if (getVotes().size() == 5 && getStatus() == STATUS_CREATING) {
            info.append("投票已完成！队长：");
            info.append(getLeaderNum());
            info.append("，队员：");
            info.append(getMembers().stream().map(AwlUser::getNum).sorted().collect(Collectors.toList()));
            List<Vote> agrees = getAgreeVotes();
            List<Vote> disAgrees = getDisAgreeVotes();
            if (agrees.size() > disAgrees.size()) {
                // 将team状态改为活动中，为接下来的task服务
                setStatus(STATUS_ACTIVE);
                // 为该team生成task
                setTask(new Task(getRuledMemberCount()));
                info.append("，组队成功！赞成的人有：");
                info.append(agrees.stream().map(Vote::getAwlUserNum).sorted().collect(Collectors.toList()));
            } else {
                setStatus(STATUS_FAIL);
                info.append("，组队失败！反对的人有：");
                info.append(disAgrees.stream().map(Vote::getAwlUserNum).sorted().collect(Collectors.toList()));
            }
        }
        return info.toString();
    }

    /**
     * after isFinishVoted
     * @return
     */
    public boolean isSuccess() {
        long agreeNum = getVotes().stream().filter(Vote::isAgree).count();
        return agreeNum > 5 - agreeNum;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }


    // 失败和成功的队伍都属于已创建完成的队伍
    /**
     * 组建队伍：失败，属于过时状态
     */
    public static final int STATUS_FAIL = -1;

    /**
     * 组建队伍：成功，属于过时状态（成功即：组队队伍成功，包含任务Task成功和失败两种状态）
     */
    public static final int STATUS_SUCCESS = 1;

    /**
     * 组建队伍成功，同时该队伍还在继续工作
     */
    public static final int STATUS_ACTIVE = 2;

    /**
     * 创建中的队伍
     */
    public static final int STATUS_CREATING = 0;

    /**
     * 规定的队伍成员人数
     */
    private static int ruledTeamMemberCount[] = {2, 3, 2, 3, 3};

}
