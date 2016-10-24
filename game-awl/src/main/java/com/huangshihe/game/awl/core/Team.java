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

    public void addMember(AwlUser awlUser) {
        if (getMembers().size() > getRuledMemberCount()) {
            getMembers().add(awlUser);
        }
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

    public boolean addVote(Vote vote) {
        // 如果以前投过票，则不允许再次投票
        if (getVotes().stream().filter(vote1 -> vote1.getAwlUserNum() == vote.getAwlUserNum()).count() != 0) {
            return false;
        }
        if (getVotes().size() < 4) {
            return getVotes().add(vote);
        } else if (getVotes().size() == 4) {
            getVotes().add(vote);
            int agrees = (int) getVotes().stream().filter(Vote::isAgree).count();
            if (agrees > getVotes().size() - agrees) {
                setStatus(STATUS_SUCCESS);
            } else {
                setStatus(STATUS_FAIL);
            }
            return true;
        }
        return false;
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
            builder.append("投票已完成！");
            List<Vote> agrees = getAgreeVotes();
            List<Vote> disAgrees = getDisAgreeVotes();
            if (agrees.size() > disAgrees.size()) {
                builder.append("组队成功！赞成的人有：");
                builder.append(agrees.stream().map(Vote::getAwlUserNum).collect(Collectors.toList()));
            }else{
                builder.append("组队失败！反对的人有：");
                builder.append(disAgrees.stream().map(Vote::getAwlUserNum).collect(Collectors.toList()));
            }
        }
        return builder.toString();
    }

    // 失败和成功的队伍都属于过期的队伍
    /**
     * 组建失败的队伍
     */
    public static final int STATUS_FAIL = -1;

    /**
     * 组建成功的队伍
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
