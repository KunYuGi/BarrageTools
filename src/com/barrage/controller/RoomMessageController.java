package com.barrage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.barrage.service.RoomMessage;

import net.sf.json.JSONObject;

@Controller
public class RoomMessageController {
	
	@Autowired
	private RoomMessage roomMessage;
	
	@RequestMapping("/roomMessage")
	@ResponseBody
	public JSONObject getRoomMessage(String roomId){
		return roomMessage.getRoomMessage(roomId);
	}
}
