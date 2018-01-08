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
 * 注册普通WebSocket
 * <p>Title:WebSocketConfig</p>
 * @author wkt
 * @date 2017年12月26日 下午4:08:08
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
		registry.addHandler(messageWebSocketHandler(), "/msg");//与客户端url对应
	}

	public WebSocketHandler messageWebSocketHandler(){
		return new WebSocketHandler(template);
	}
}
