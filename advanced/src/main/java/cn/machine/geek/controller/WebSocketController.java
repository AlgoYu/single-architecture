package cn.machine.geek.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

/**
 * @Author: MachineGeek
 * @Description: WebSocket控制器
 * @Email: 794763733@qq.com
 * @Date: 2021/1/11
 */
@Component
@ServerEndpoint("/websocket")
@Slf4j
public class WebSocketController {
    @OnOpen
    public void onOpen(Session session){
        log.info(session.getId()+"连接");
    }

    @OnClose
    public void onClose(Session session){
        log.info(session.getId()+"关闭");
    }

    @OnMessage
    public void onMessage(String message){
        log.info(message);
    }

    @OnError
    public void onError(Session session,Throwable throwable){
        log.info(session.getId()+"错误");
        log.info("错误"+throwable.getMessage());
    }
}
