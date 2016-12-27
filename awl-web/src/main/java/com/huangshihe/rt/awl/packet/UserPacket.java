package com.huangshihe.rt.awl.packet;

import com.huangshihe.game.core.GameUser;
import com.huangshihe.rt.model.User;
import com.huangshihe.rt.util.GlobalUtils;

/**
 * Created by Administrator on 2016/8/15.
 */
public class UserPacket {
    /**
     * 用户id
     */
    private int userId;

    /**
     * 用户名
     */
    private String username;
    /**
     * 性别
     */
    private boolean sex;
    /**
     * 头像地址
     */
    private String photo;
    /**
     * 编号
     */
    private int identityNum;
    /**
     * 身份类型
     */
    private int identityType;
    /**
     * 身份名
     */
    private String identityName;
    /**
     * 身份描述
     */
    private String identityDescription;
    /**
     * 身份特殊信息
     */
    private String identityInfo;

    public UserPacket(GameUser gameUser) {
        if (gameUser != null) {
            User user = User.getUser(gameUser.getUserId());
            setUserId(user.getId());
            setUsername(user.getUsername());
            setSex(user.getSex());
            // 当用户没有头像时，使用默认头像
            if (user.getPhoto() == null || "".equals(user.getPhoto().trim())) {
                setPhoto(GlobalUtils.RESOURCE_LOCATION + "user_placeholder.png");
            } else {
                setPhoto(user.getPhoto());
            }
            // 只有当玩家处于游戏状态时（活着），才具备玩家身份信息。
            if (gameUser.getStatus() == GameUser.LIVE) {
                setIdentityNum(gameUser.getNum());
                setIdentityType(gameUser.getIdentity().getType());
                setIdentityName(gameUser.getIdentity().getName());
                setIdentityDescription(gameUser.getIdentity().getDescription());
                setIdentityInfo(gameUser.getInfo());
            }
        }
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getIdentityNum() {
        return identityNum;
    }

    public void setIdentityNum(int identityNum) {
        this.identityNum = identityNum;
    }

    public int getIdentityType() {
        return identityType;
    }

    public void setIdentityType(int identityType) {
        this.identityType = identityType;
    }

    public String getIdentityName() {
        return identityName;
    }

    public void setIdentityName(String identityName) {
        this.identityName = identityName;
    }

    public String getIdentityDescription() {
        return identityDescription;
    }

    public void setIdentityDescription(String identityDescription) {
        this.identityDescription = identityDescription;
    }

    public String getIdentityInfo() {
        return identityInfo;
    }

    public void setIdentityInfo(String identityInfo) {
        this.identityInfo = identityInfo;
    }

    @Override
    public String toString() {
        return "UserPacket{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", sex=" + sex +
                ", photo='" + photo + '\'' +
                ", identityNum=" + identityNum +
                ", identityType=" + identityType +
                ", identityName='" + identityName + '\'' +
                ", identityDescription='" + identityDescription + '\'' +
                ", identityInfo='" + identityInfo + '\'' +
                '}';
    }
}
