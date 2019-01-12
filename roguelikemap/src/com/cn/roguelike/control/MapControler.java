package com.cn.roguelike.control;

import com.cn.roguelike.bean.MapBean;
import com.cn.roguelike.bean.RoomBean;
import com.cn.roguelike.util.LLAssert;
public class MapControler {
	/** 地图X最大值*/
	private int maxXsize=100;
	
	/** 地图Y最大值*/
	private int maxYsize=100;
	
	/** 地图最大房间数量*/
	private int maxRoomNum=50;
	
	private MapBean mapBean;
	
	private static MapControler instance = new MapControler();
	
	public static MapControler getInstance() 
	{
	    return instance;
	}

	public int getMaxXsize() {
		return maxXsize;
	}

	public void setMaxXsize(int maxXsize) {
		this.maxXsize = maxXsize;
	}

	public int getMaxYsize() {
		return maxYsize;
	}

	public void setMaxYsize(int maxYsize) {
		this.maxYsize = maxYsize;
	}
	
	public int getMaxRoomNum() {
		return maxRoomNum;
	}

	public void setMaxRoomNum(int maxRoomNum) {
		this.maxRoomNum = maxRoomNum;
	}

	public void initMapInfo() {
		mapBean=new MapBean(maxXsize,maxYsize);
	}
	
	public void creatRoomByMap(){
		//循环生成房间
		RoomBean roomBean=null;
		for(int i=0;i<maxRoomNum;i++) {
			roomBean=new RoomBean();
			roomBean.creatRoom(1, maxXsize, maxYsize);
			if(!mapBean.checkCanAddRoomNoConnect(roomBean, maxXsize, maxYsize)) continue;
			roomBean.setRoomId(mapBean.getMaxRoomId()+i);
			mapBean.addRoomToMap(roomBean);
		}
	}
	
	public void printMapInfo() {
		LLAssert.isTrue(mapBean!=null,"map is null!");
		LLAssert.isTrue(mapBean.getArrPoint().length>0,"map is empty!");
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<maxXsize;i++) {
			for(int j=0;j<maxYsize;j++) {
				sb.append(String.valueOf(mapBean.getArrPoint()[i][j]));
			}
			sb.append("\r\n");
		}
		System.out.println(sb.toString());
	}
	
	
	
}
