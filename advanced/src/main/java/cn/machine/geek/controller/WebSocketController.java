package cn.machine.geek.controller;

import cn.machine.geek.util.PubSubUtil;
import cn.machine.geek.util.WebSocketSessionManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @Author: MachineGeek
 * @Description: WebSocket、Redis订阅处理器
 * @Email: 794763733@qq.com
 * @Date: 2020/11/11
 */
@Slf4j
@Component
public class WebSocketController extends TextWebSocketHandler implements MessageListener {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PubSubUtil pubSubUtil;
    public static ChannelTopic WEB_SOCKET_CHANNEL_TOPIC = new ChannelTopic("WebSocket");
    /**
    * @Author: MachineGeek
    * @Description: WebSocket连接成功
    * @Date: 2020/11/11
     * @param session
    * @Return: void
    */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        WebSocketSessionManager.put(session.getAttributes().get("id").toString(),session);
    }

    /**
    * @Author: MachineGeek
    * @Description: 接受客户端消息
    * @Date: 2020/11/11
     * @param session
     * @param message
    * @Return: void
    */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 往Redis的频道发送
        pubSubUtil.publish(WEB_SOCKET_CHANNEL_TOPIC.getTopic(),message.getPayload());
    }

    /**
    * @Author: MachineGeek
    * @Description: WebSocket连接关闭
    * @Date: 2020/11/11
     * @param session
     * @param status
    * @Return: void
    */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        WebSocketSessionManager.remove(session.getAttributes().get("id").toString());
    }

    /**
    * @Author: MachineGeek
    * @Description: 传输出错
    * @Date: 2020/11/11
     * @param session
     * @param exception
    * @Return: void
    */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        WebSocketSessionManager.remove(session.getAttributes().get("id").toString());
    }

    /**
    * @Author: MachineGeek
    * @Description: 订阅Redis发来的消息
    * @Date: 2020/11/12
     * @param message
     * @param bytes
    * @Return: void
    */
    @Override
    public void onMessage(Message message, byte[] bytes) {
        try {
            String data = StringEscapeUtils.unescapeJson(message.toString());
            JSONObject jsonObject = new JSONObject(data.substring(1,data.length()-1));
            WebSocketSessionManager.sendText(jsonObject.getString("id"),new TextMessage(jsonObject.getString("data")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
