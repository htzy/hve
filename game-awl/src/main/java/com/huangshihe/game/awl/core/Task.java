package com.huangshihe.game.awl.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/26.
 */
public class Task {
    private List<Vote> votes;
    private int needs;

    public Task(int needs) {
        this.needs = needs;
    }

    public List<Vote> getVotes() {
        if (votes == null) {
            votes = new ArrayList<Vote>(needs + 1);
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
        return getVotes().add(new Vote(awlUserNum, answer));
    }

    /**
     * 判断任务是否已经做完，即是否已经达到needs需要的票数
     * @return
     */
    public boolean isDone() {
        return getVotes().size() == needs;
    }

    /**
     * 返回任务是否成功，当出现'×'(false)，即为失败
     * @return
     */
    public boolean isSuccess() {
        return getVotes().stream().filter(Vote::isAgree).count() == needs;
    }


    @Override
    public String toString() {
        return "Task{" +
                "votes=" + votes +
                '}';
    }
}
