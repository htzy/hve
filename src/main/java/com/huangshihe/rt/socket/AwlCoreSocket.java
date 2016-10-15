package com.huangshihe.rt.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    private User user;

    private int creatorId;

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * WebSocket Session
     */
    private Session session;

    public AwlCoreSocket() {
    }

    /**
     * 打开连接
     * @param session
     * @param creatorId
     * @param awlUserId
     */
    @OnOpen
    public void onOpen(Session session,
                       @PathParam(value = "creatorId") int creatorId,
                       @PathParam(value = "awlUserId") int awlUserId) {

        this.session = session;
        this.creatorId = creatorId;
        Awl awl = AwlCache.getInstance().get(creatorId);
        // 游戏存在且处于等待状态
        // TODO 暂不考虑断线
        if(awl == null || !(awl.getStatus() == Awl.STATUS_WAIT || awl.getStatus() == Awl.STATUS_DELETE_GAMER)){
            throw new IllegalArgumentException("illegal game of awl, wrong creatorId or status");
        }
        //查询用户信息
        user = User.dao.findById(awlUserId);
        if(user == null){
            throw new IllegalArgumentException("illegal user visit...");
        }
        // 该玩家加入到游戏中
        awl.add(new AwlUser(awlUserId));
        connections.add(this);

        BasePacket basePacket = new BasePacket(creatorId, awl);
        try {
            AwlCoreSocket.broadCast(objectMapper.writeValueAsString(basePacket));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
//
//        // 当人数达到要求时，分配角色并提示客户端游戏开始。
//        if(awl.getGamers().size() == awl.getRequireGamerNum()){
//            if(awl.play()){
//                // 队伍创建者从num-0开始，根据玩家num获得玩家id
//                int teamCreatorId = awl.getGameUserFromNum(0).getUserId();
//                basePacket = new BasePacket(creatorId, awl);
////                FullPacket fullPacket = new FullPacket(new BasePacket(awlUserId, awl), new TeamPacket(teamCreatorId, null));
//                // 发送基本信息包和组队信息包
//                try {
//                    AwlCoreSocket.broadCast(objectMapper.writeValueAsString(basePacket));
////                    System.out.println("objectMapper.writeValueAsString(fullPacket) = " + objectMapper.writeValueAsString(fullPacket));
////                    AwlCoreSocket.broadCast(objectMapper.writeValueAsString(fullPacket));
//                } catch (JsonProcessingException e) {
//                    e.printStackTrace();
//                }
////                AwlCoreSocket.broadCast("系统提示 > 游戏正式开始！别xia bb了...");
//            }else{
//                AwlCoreSocket.broadCast("系统提示 > 分配角色失败！请联系开发者。");
//            }
//        }
    }

    /**
     * 关闭连接
     */
    @OnClose
    public void onClose(@PathParam(value = "awlUserId") int awlUserId) {
        connections.remove(this);
        // TODO 当有玩家掉线时，如何处理？
        // 当有玩家掉线，更新当前游戏玩家信息（去除该掉线玩家），并通知客户端
        Awl awl = AwlCache.getInstance().get(creatorId);
        BasePacket basePacket = new BasePacket(awlUserId, awl.getGamer(awlUserId), Awl.STATUS_DELETE_GAMER);
        try {
            AwlCoreSocket.broadCast(objectMapper.writeValueAsString(basePacket));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        awl.remove(awl.getGamer(awlUserId));
        System.out.println("close core socket connection awl.getGamers().size() = " + awl.getGamers().size());
    }

    /**
     * 接收信息
     *
     * @param message
     * @param awlUserId
     */
    @OnMessage
    public void onMessage(String message,
                          @PathParam(value = "awlUserId") int awlUserId) {
        // TODO 规定接收数据方式，解析message信息
        AwlCoreSocket.broadCast("hello:" + ">" + message);
    }

    /**
     * 错误信息响应
     *
     * @param throwable
     */
    @OnError
    public void onError(Throwable throwable) {
        System.err.println("awl core socket error:"+throwable.getLocalizedMessage());
        throwable.printStackTrace();
    }

    /**
     * 发送或广播信息
     *
     * @param message
     */
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
                } catch (IOException e1) {
                }
//                AwlCoreSocket.broadCast(String.format("System> %s %s", socket.nickName,
//                        " has bean disconnection."));
            }
        }
    }


}
