package com.huangshihe.rt.awl.packet;

import com.huangshihe.game.awl.core.Task;

/**
 * Created by Administrator on 2016/8/15.
 */
public class TaskPacket {
    /**
     * 任务创建者id
     */
    private int creatorId;
    /**
     * 任务回复项大小
     */
    private int itemSize;

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
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
                "creatorId=" + getCreatorId() +
                ", itemSize=" + getItemSize() +
                '}';
    }
}
