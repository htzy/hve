package com.huangshihe.rt.awl.packet;

import com.huangshihe.game.awl.core.Task;
import com.huangshihe.game.awl.core.Vote;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016/8/15.
 */
public class TaskPacket {

    private List<Integer> members;

    private boolean result;

    public TaskPacket() {
    }

    public TaskPacket(Task task) {
        if (task != null){
            getMembers().addAll(task.getVotes().stream().map(Vote::getAwlUserNum).collect(Collectors.toList()));
            if (task.isDone()) {
                result = task.isSuccess();
            }
        }
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

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "TaskPacket{" +
                "members=" + members +
                ", result=" + result +
                '}';
    }
}
