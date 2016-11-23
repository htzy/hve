package com.huangshihe.rt.awl.packet;

import com.huangshihe.game.awl.core.Task;

import java.util.List;

/**
 * Created by Administrator on 2016/8/15.
 */
public class TaskPacket {

    private List<Integer> members;

    private boolean result;

    public TaskPacket() {
    }

    public TaskPacket(Task task){
        // TODO next task??
    }

    public TaskPacket(List<Integer> members, boolean result) {
        this.members = members;
        this.result = result;
    }

    public List<Integer> getMembers() {
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
