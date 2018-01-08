package com.barrage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.barrage.client.DyBulletScreenClient;
import com.barrage.utils.KeepAlive;
import com.barrage.utils.KeepGetMsg;
import com.barrage.websocket.WebSocketTest;

@Controller
public class PageController {
	@RequestMapping("/{page}")
	public String jumpToPage(@PathVariable("page") String page){
		return page;
	}
}
