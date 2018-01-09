package com.barrage.service;

import org.springframework.stereotype.Service;

import com.barrage.constant.Constants;
import com.barrage.utils.HttpRequestUtils;

import net.sf.json.JSONObject;

@Service
public class RoomMessage {
	
	public JSONObject getRoomMessage(String roomId){
		String url = Constants.ROOM_MESSAGE_URL + roomId;
		JSONObject jsonObject = HttpRequestUtils.httpGet(url);
		JSONObject data = (JSONObject) jsonObject.get("data");
		return data;
	}
}
