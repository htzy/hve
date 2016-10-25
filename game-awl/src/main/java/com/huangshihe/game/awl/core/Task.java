package com.huangshihe.game.awl.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/26.
 */
public class Task {
    private List<Vote> votes;

    public List<Vote> getVotes() {
        if(votes == null){
            votes = new ArrayList<Vote>();
        }
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public boolean add(int awlUserNum, boolean answer) {
        // 以前做过该任务时，不允许再做
        if (getVotes().stream().filter(vote -> vote.getAwlUserNum() == awlUserNum).count() != 0) {
            return false;
        }
        return getVotes().add(new Vote(awlUserNum,answer));
    }

    @Override
    public String toString() {
        return "Task{" +
                "votes=" + votes +
                '}';
    }
}
