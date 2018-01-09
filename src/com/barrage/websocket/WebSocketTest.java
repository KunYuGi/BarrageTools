package com.barrage.websocket;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import com.barrage.client.DyBulletScreenClient;
import com.barrage.utils.KeepAlive;
import com.barrage.utils.KeepGetMsg;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@ServerEndpoint("/websocket")
public class WebSocketTest {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<WebSocketTest> webSocketSet = new CopyOnWriteArraySet<WebSocketTest>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    
    private DyBulletScreenClient client;
    private KeepGetMsg keepGetMsg;

    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        client = DyBulletScreenClient.getInstance();
//        webSocketSet.add(this);     //加入set中
//        addOnlineCount();           //在线数加1
//        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
    	System.out.println("closed");
    	client.setReadyFlag(false);
    	keepGetMsg.flag = true;
    	keepGetMsg.session = null;
//        webSocketSet.remove(this);  //从set中删除
//        subOnlineCount();           //在线数减1
//        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    	
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message) {
    	System.out.println("房间id:" + message);
        int roomId = Integer.parseInt(message);
        int groupId = -9999;
        String info = client.init(roomId, groupId);
        sendMessage(info);
        KeepAlive keepAlive = new KeepAlive();
        keepAlive.start();
        keepGetMsg = new KeepGetMsg(this.session);
        keepGetMsg.start();
//        while(true){
//        	if (!client.getReadyFlag()) {
//				break;
//			}
//        	List<Map<String,Object>> list = client.getServerMessage();
//        	for(int i = 0;i < list.size(); i++){
//        		Map<String, Object> map = list.get(i);
//        		try {
//        			String msg = map.toString();
//					sendMessage(msg);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//        	}
//        }
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
    	System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message){
        try {
			this.session.getBasicRemote().sendText(message);
			//this.session.getAsyncRemote().sendText(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketTest.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketTest.onlineCount--;
    }
}
