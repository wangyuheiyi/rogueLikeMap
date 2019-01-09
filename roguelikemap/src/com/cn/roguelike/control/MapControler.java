package com.cn.roguelike.control;

import com.cn.roguelike.bean.MapBean;
import com.cn.roguelike.control.rogueLikeEum.MapInfoType;

public class MapControler {
	/** 地图X最大值*/
	private int maxXsize=100;
	
	/** 地图Y最大值*/
	private int maxYsize=100;
	
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
	
	public void initMapInfo() {
		mapBean=new MapBean(maxXsize,maxYsize);
		for(int i=0;i<maxXsize;i++) {
			for(int j=0;j<maxYsize;j++) {
				mapBean.getArrMap()[i][j]=MapInfoType.FLOOR.getIndex();
			}
		}
	}
	
	public void printMapInfo() {
		if(mapBean==null) {
			System.out.println("map is null!");
			return;
		}
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<maxXsize;i++) {
			for(int j=0;j<maxYsize;j++) {
				sb.append(String.valueOf(mapBean.getArrMap()[i][j]));
			}
			sb.append("\r\n");
		}
		System.out.println(sb.toString());
	}
}
