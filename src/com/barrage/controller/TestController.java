package com.barrage.controller;


import com.barrage.utils.HttpRequestUtils;

import net.sf.json.JSONObject;

public class TestController {
	private static String HOST_NAME = "http://open.douyucdn.cn/api/RoomApi/room/";
	public static void main(String[] args) {
		JSONObject jsonObject = HttpRequestUtils.httpGet(HOST_NAME + "213116");
		JSONObject data = (JSONObject) jsonObject.get("data");
		String ownerName = (String) data.get("owner_name");
		String hn = (String) data.get("cate_name");
		String roomStatus = (String) data.get("room_status");
		System.out.println(roomStatus);
	}
}
