package com.huangshihe.rt.awl.packet;

import com.huangshihe.game.awl.core.Task;

/**
 * Created by Administrator on 2016/8/15.
 */
public class TaskPacket {
    /**
     * 任务创建者id
     */
    private int creatorNum;
    /**
     * 任务回复项大小
     */
    private int itemSize;

    public TaskPacket() {
    }

    public TaskPacket(int creatorNum, int itemSize) {
        this.creatorNum = creatorNum;
        this.itemSize = itemSize;
    }

    public int getCreatorNum() {
        return creatorNum;
    }

    public void setCreatorNum(int creatorNum) {
        this.creatorNum = creatorNum;
    }

    public int getItemSize() {
        return itemSize;
    }

    public void setItemSize(int itemSize) {
        if(itemSize == Task.BAD_ITEM_SIZE || itemSize == Task.GOOD_ITEM_SIZE){
            this.itemSize = itemSize;
        }
    }

    @Override
    public String toString() {
        return "TaskPacket{" +
                "creatorNum=" + getCreatorNum() +
                ", itemSize=" + getItemSize() +
                '}';
    }
}
