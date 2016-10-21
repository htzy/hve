package com.huangshihe.rt.awl.packet;

/**
 * Created by Administrator on 2016/10/22.
 */
public class MessagePacket {
    private String operate;
    private String data;

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MessagePacket{" +
                "operate='" + operate + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
