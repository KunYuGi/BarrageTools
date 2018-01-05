package com.barrage.controller;


import com.barrage.client.DyBulletScreenClient;
import com.barrage.utils.KeepAlive;
import com.barrage.utils.KeepGetMsg;


public class TestController {
	public static void main(String[] args) {
		DyBulletScreenClient client = DyBulletScreenClient.getInstance();
        int roomId = 60062;
        int groupId = -9999;
        client.init(roomId, groupId);
        KeepAlive keepAlive = new KeepAlive();
        keepAlive.start();
        KeepGetMsg keepGetMsg = new KeepGetMsg();
        keepGetMsg.start();
	}
}
