package com.huangshihe.rt.socket;

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
@Deprecated
@ServerEndpoint(value = "/ws/awl/chat/{awlUserId}")
public class AwlChatSocket {
    /**
     * 连接对象集合
     */
    private static final Set<AwlChatSocket> connections = new CopyOnWriteArraySet<AwlChatSocket>();

//    /**
//     * 隐私对象集合
//     */
//    private static final Set<AwlChatSocket> privateConnections = new CopyOnWriteArraySet<AwlChatSocket>();

    private User user;

    /**
     * WebSocket Session
     */
    private Session session;

    public AwlChatSocket() {
    }

    /**
     * 打开连接
     *
     * @param session
     * @param awlUserId
     */
    @OnOpen
    public void onOpen(Session session,
                       @PathParam(value = "type") String type,
                       @PathParam(value = "awlUserId") int awlUserId) {

        this.session = session;
        user = User.dao.findById(awlUserId);
        if (user == null) {
            throw new IllegalArgumentException("illegal user visit...");
        }
        connections.add(this);
        String message = String.format("系统提示 > %s已上线！", user.getUsername());
        AwlChatSocket.broadCast(message);
    }

    /**
     * 关闭连接
     */
    @OnClose
    public void onClose() {
        connections.remove(this);
        String message = String.format("系统提示 > %s已掉线或退出！", user.getUsername());
        AwlChatSocket.broadCast(message);
    }

    /**
     * 接收信息
     *
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        // TODO
        AwlChatSocket.broadCast(user.getUsername() + " > " + message);
    }

    /**
     * 错误信息响应
     *
     * @param throwable
     */
    @OnError
    public void onError(Throwable throwable) {
        System.out.println(throwable.getMessage());
    }

    /**
     * 发送或广播信息
     *
     * @param message
     */
    private static void broadCast(String message) {
        for (AwlChatSocket socket : connections) {
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
                AwlChatSocket.broadCast(String.format("系统提示 > %s已意外掉线！", socket.user.getUsername()));
            }
        }
    }
}
