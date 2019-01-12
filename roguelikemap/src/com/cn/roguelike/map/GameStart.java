package com.cn.roguelike.map;

import com.cn.roguelike.control.MapControler;

public class GameStart {
	public static void main(String arg[]) {
		MapControler.getInstance().initMapInfo();
		MapControler.getInstance().creatRoomByMap();
		MapControler.getInstance().printMapInfo();
	}
}
