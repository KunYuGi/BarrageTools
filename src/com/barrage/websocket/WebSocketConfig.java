package com.barrage.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * ע����ͨWebSocket
 * <p>Title:WebSocketConfig</p>
 * @author wkt
 * @date 2017��12��26�� ����4:08:08
 */
@Component
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer{
	@Autowired
	@Lazy
	private SimpMessagingTemplate template;
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// TODO Auto-generated method stub
		registry.addHandler(messageWebSocketHandler(), "/msg");//��ͻ���url��Ӧ
	}

	public WebSocketHandler messageWebSocketHandler(){
		return new WebSocketHandler(template);
	}
}
