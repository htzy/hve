package com.huangshihe.rt.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huangshihe.game.awl.core.Awl;
import com.huangshihe.game.awl.core.AwlUser;
import com.huangshihe.game.awl.manage.AwlCache;
import com.huangshihe.rt.awl.packet.BasePacket;
import com.huangshihe.rt.model.User;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by root on 5/16/16.
 */
@ServerEndpoint(value = "/ws/awl/core/{creatorId}/{awlUserId}")
public class AwlCoreSocket {
    /**
     * 连接对象集合
     * TODO 该变量断线时可用，当断线后awl中的gamers与该变量（连接数）一定不一样
     */
    private static final Set<AwlCoreSocket> connections = new CopyOnWriteArraySet<AwlCoreSocket>();

    // TODO change set to map??
//    private static final Map<String,Object> connections = new HashMap<String,Object>();

    private User user;

    private int creatorId;

    private int awlUserId;

    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * WebSocket Session
     */
    private Session session;


    /**
     * 打开连接
     *
     * @param session
     * @param mCreatorId
     * @param mAwlUserId
     */
    @OnOpen
    public void onOpen(Session session,
                       @PathParam(value = "creatorId") int mCreatorId,
                       @PathParam(value = "awlUserId") int mAwlUserId) {

        this.session = session;
        this.creatorId = mCreatorId;
        this.awlUserId = mAwlUserId;
        Awl awl = AwlCache.getInstance().get(creatorId);
        // 当玩家掉线后重新进入时，重新设置游戏状态为等待
        if (awl != null && awl.getStatus() == Awl.STATUS_DELETE_GAMER) {
            awl.setStatus(Awl.STATUS_WAIT);
        }
        // 游戏存在且处于等待状态
        if (awl == null || awl.getStatus() != Awl.STATUS_WAIT) {
            throw new IllegalArgumentException("illegal game of awl, wrong creatorId or status");
        }
        //查询用户信息
        user = User.dao.findById(awlUserId);
        if (user == null) {
            throw new IllegalArgumentException("illegal user visit...");
        }
        // 该玩家加入到游戏中
        awl.add(new AwlUser(awlUserId));
        connections.add(this);

        AwlCoreSocket.broadCast(new BasePacket(awl));
    }

    /**
     * 关闭连接
     */
    @OnClose
    public void onClose() {
        connections.remove(this);
        // 当有玩家掉线，更新当前游戏玩家信息（去除该掉线玩家），并通知客户端
        Awl awl = AwlCache.getInstance().get(creatorId);
        BasePacket basePacket;
        // 当创建者退出时，结束该游戏；当参与者退出时，从玩家列表中删除该玩家
        if (awl == null || creatorId == awlUserId) {
            basePacket = new BasePacket(null, Awl.STATUS_ED);
        } else {
            basePacket = new BasePacket(awl.getGamer(awlUserId), Awl.STATUS_DELETE_GAMER);
            awl.remove(awl.getGamer(awlUserId));
        }
        AwlCoreSocket.broadCast(basePacket);
    }

    /**
     * 接收信息
     *
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        // TODO 规定接收数据方式，解析message信息
        AwlCoreSocket.broadCast(user.getUsername() + ">" + message);
    }

    /**
     * 错误信息响应
     *
     * @param throwable
     */
    @OnError
    public void onError(Throwable throwable) {
        System.err.println("awl core socket error:" + throwable.getLocalizedMessage());
        throwable.printStackTrace();
    }

    /**
     * 发送或广播信息
     *
     * @param message
     */
    @Deprecated
    private static void broadCast(String message) {
        for (AwlCoreSocket socket : connections) {
            try {
                synchronized (socket) {
                    socket.session.getBasicRemote().sendText(message);
                }
            } catch (IOException e) {
                connections.remove(socket);
                try {
                    socket.session.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    /**
     * 发送或广播信息
     *
     * @param basePacket
     */
    private static void broadCast(BasePacket basePacket) {
        for (AwlCoreSocket socket : connections) {
            try {
                synchronized (socket) {
                    // 设置发送人
                    basePacket.setAwlUserId(socket.awlUserId);
                    socket.session.getBasicRemote().sendText(objectMapper.writeValueAsString(basePacket));
                }
            } catch (IOException e) {
                connections.remove(socket);
                try {
                    socket.session.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

}
