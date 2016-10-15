package com.huangshihe.game.awl.core;

import com.huangshihe.game.awl.manage.AssignAwlIdentity;
import com.huangshihe.game.core.Game;
import com.huangshihe.game.core.GameUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/7/23.
 */
public class Awl implements Game {
    private int creatorId;
    private String creatorName;
    private Date createTime;
    private int requireGamerNum;
    private int status;
    private int currentLeaderNum;
    private int totalTaskSuccessCount;
    private int totalTaskFailCount;

    private List<GameUser> awlUsers;

    /**
     * 组建的队伍
     */
    private List<Team> teamList;

    /**
     * 因为一个用户只能创建一个阿瓦隆游戏，因此在这awl_id用创建人id即creatorId。
     *
     * @param id          equal creatorId
     * @param creatorId
     * @param creatorName
     */
    public Awl(int id, int creatorId, String creatorName) {
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        createTime = new Date();
        // 默认需要的游戏人数为5。
        requireGamerNum = 5;

        awlUsers = new ArrayList<GameUser>(requireGamerNum);
        // 添加游戏创建者作为玩家
        add(new AwlUser(creatorId));
        status = STATUS_WAIT;
    }

    /**
     * 游戏开始
     */
    public boolean play() {
        try {
            // 分配角色
            new AssignAwlIdentity().assign(this);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getLocalizedMessage());
            return false;
        }
        // 初始化
        currentLeaderNum = 0;
        totalTaskSuccessCount = 0;
        totalTaskFailCount = 0;
        // 游戏开始
        status = STATUS_ING;
        return true;
    }

    public GameUser getGameUserFromNum(int num) {
        return getGamers().stream().filter(gameUser -> gameUser.getNum() == num).findFirst().orElse(null);
    }


    @Override
    public int getId() {
        return creatorId;
    }

    @Override
    public void setId(int id) {
        this.creatorId = id;
    }

    @Override
    public int getCreatorId() {
        return creatorId;
    }

    @Override
    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    @Override
    public String getCreatorName() {
        return creatorName;
    }

    @Override
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public List<GameUser> getGamers() {
        return awlUsers;
    }

    @Override
    public boolean add(GameUser gameUser) {
        if (getGamers().stream().filter(temp -> temp.getUserId() == gameUser.getUserId()).findFirst().orElse(null) == null) {
            // 当人数达到要求，则启动游戏
            if (this.getRequireGamerNum() == this.getGamers().size() + 1) {
                if (this.awlUsers.add(gameUser)) {
                    return this.play();
                }
            }
            return awlUsers.add(gameUser);
        }
        return false;
    }

    @Override
    public boolean remove(GameUser gameUser) {
        setStatus(STATUS_DELETE_GAMER);
        return awlUsers.remove(gameUser);
    }

    @Override
    public GameUser getGamer(int userId) {
        return getGamers().stream().filter(awlUser -> awlUser.getUserId() == userId).findFirst().orElse(null);
    }

    @Override
    public int getRequireGamerNum() {
        return requireGamerNum;
    }

    @Override
    public void setRequireGamerNum(int requireGamerNum) {
        this.requireGamerNum = requireGamerNum;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public void setStatus(int status) {
        this.status = status;
    }

    public List<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<Team> teamList) {
        this.teamList = teamList;
    }

    public boolean createTeam(Team team) {
        return getTeamList().add(team);
    }

    public int getCurrentLeaderNum() {
        return currentLeaderNum;
    }

    public void setCurrentLeaderNum(int currentLeaderNum) {
        this.currentLeaderNum = currentLeaderNum;
    }

    public int getTotalTaskSuccessCount() {
        return totalTaskSuccessCount;
    }

    public void setTotalTaskSuccessCount(int totalTaskSuccessCount) {
        this.totalTaskSuccessCount = totalTaskSuccessCount;
    }

    public int getTotalTaskFailCount() {
        return totalTaskFailCount;
    }

    public void setTotalTaskFailCount(int totalTaskFailCount) {
        this.totalTaskFailCount = totalTaskFailCount;
    }

    /**
     * 游戏状态：进行中
     */
    public static final int STATUS_ING = 1;

    /**
     * 游戏状态：已结束
     */
    public static final int STATUS_ED = 2;

    /**
     * 游戏状态：等待中
     */
    public static final int STATUS_WAIT = 0;

    /**
     * 状态：删除玩家
     */
    public static final int STATUS_DELETE_GAMER = -1;

}
