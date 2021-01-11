package cn.machine.geek.util;

import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: MachineGeek
 * @Description: WebSocket Session 管理器
 * @Email: 794763733@qq.com
 * @Date: 2021/1/11
 */
public class WebSocketSessionManager {
    // WebSocket连接池
    private static ConcurrentHashMap<String, WebSocketSession> WebSocketSessionPool = new ConcurrentHashMap<String, WebSocketSession>();

    /**
     * @Author: MachineGeek
     * @Description: 存入WebSocketSession
     * @Date: 2020/11/11
     * @param key
     * @param webSocketSession
     * @Return: void
     */
    public static void put(String key, WebSocketSession webSocketSession){
        WebSocketSessionPool.put(key,webSocketSession);
    }

    /**
     * @Author: MachineGeek
     * @Description: 获取WebSocketSession
     * @Date: 2020/11/11
     * @param key
     * @Return: javax.websocket.Session
     */
    public static WebSocketSession get(String key){
        return WebSocketSessionPool.get(key);
    }

    /**
     * @Author: MachineGeek
     * @Description: 删除WebSocketSession
     * @Date: 2020/11/11
     * @param key
     * @Return: void
     */
    public static void remove(String key){
        WebSocketSession webSocketSession = WebSocketSessionPool.remove(key);
        if(webSocketSession!=null){
            try {
                webSocketSession.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
