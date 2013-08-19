package com.luo.server;

import java.util.logging.Logger;

import org.jboss.netty.channel.MessageEvent;

public class MessageDispatchHandler {
    private ChatData            chatData;
    private ChatConnection        connection;
 
    //事件监听器
    private ChartRoomListener    listener;
 
    public MessageDispatchHandler(ChatConnection connection, Object message) {
        this.chatData = ChatPackageUtil.unpackage(message);
        this.connection = connection;
    }
 
    public void dispatch() {
        if (listener == null) {
            throw new RuntimeException("监听器不能为空!");
        }
        try {
            switch (chatData.getMethod()) {
                case C_CONN:
                    listener.connChatRoom(connection, chatData);
                    break;
                case C_JOIN:
                    listener.loginChatRoom(connection, chatData);
                    break;
                case C_PUBLISH_MSG:
                    listener.broadcastMsg(connection, chatData);
                    break;
                case C_SEND_ADMIN:
                    listener.sendAdminMsg(connection, chatData);
                    break;
                case C_SEND_NORMAL:
                    listener.sendNormalMsg(connection, chatData);
                    break;
                case C_GET_USERS:
                    listener.getOnlineUserList(connection, chatData);
                    break;
                default:
                    throw new BusinessException("错误指令：" + chatData.getMethod());
            }
        } catch (BusinessException e) {
            ChatService.pushErrorMsg(e.getCode(), e.getMessage(), connection);
        }
    }
 
    public void setListener(ChartRoomListener listener) {
        this.listener = listener;
    }
 
}
