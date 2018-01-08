package com.barrage.websocket;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class WebSocketHandler extends TextWebSocketHandler{
	private SimpMessagingTemplate template;
	
	public WebSocketHandler(SimpMessagingTemplate template){
		this.template = template;
		System.out.println("��ʼ��handler");
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String text = message.getPayload();//��ȡ�ύ��������Ϣ
		System.out.println("handlerMessage:" + text);
		session.sendMessage(message);
	}
}
