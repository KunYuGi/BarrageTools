package com.barrage.utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.websocket.Session;

import com.barrage.client.DyBulletScreenClient;

import net.sf.json.JSONObject;

/**
 * @Summary: 获取服务器弹幕信息线程
 * @author: FerroD
 * @date: 2016-3-12
 * @version V1.0
 */
public class KeepGetMsg extends Thread {
	public Session session;
	public boolean flag = false;
	private KeepGetMsg() {
	}
	
	public KeepGetMsg(Session session){
		this.session = session;
	}
	@Override
	public void run() {
		// //获取弹幕客户端
		DyBulletScreenClient danmuClient = DyBulletScreenClient.getInstance();
		// 判断客户端就绪状态
		while (danmuClient.getReadyFlag()) {
			if (flag) {
				break;
			}
			// 获取服务器发送的弹幕信息
			List<Map<String,Object>> list = danmuClient.getServerMessage();
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				JSONObject jsonObject = JSONObject.fromObject(map);
				try {
					sendMessage(jsonObject.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void sendMessage(String message) throws IOException{
		if (session != null) {
			this.session.getBasicRemote().sendText(message);
			//this.session.getAsyncRemote().sendText(message);
		}
    }
}
