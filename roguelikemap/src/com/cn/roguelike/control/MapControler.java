package com.cn.roguelike.control;

import java.util.ArrayList;
import java.util.List;

import com.cn.roguelike.bean.MapBean;
import com.cn.roguelike.bean.PointBean;
import com.cn.roguelike.bean.RoomBean;
import com.cn.roguelike.control.rogueLikeEum.MapInfoType;
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
	
	/**
	 * 创建房间
	 */
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
	
	public void creatCorridor(){
		//循环获取一个初始点，周围两格子没有其他物体
		for(int i=0;i<maxXsize;i++) {
			for(int j=0;j<maxYsize;j++) {
				if(mapBean.getArrPoint()[i][j]!=MapInfoType.FLOOR.getIndex()) continue;
				floodFill4(-1,-1,i,j);
			}
		}
	}
	
	/**
	 * 4方向填充算法
	 */
	private void floodFill4(int oldX,int oldY,int newX,int newY){
		//判断新的点是否可以画，如果是第一个点就判断4个方向 如果是新点就判断3个方向
		List<PointBean> pointCanCorridors=new ArrayList<PointBean>();
		if(newX-1>=0&&!checkIsOldPoint(oldX,oldY,newX-1,newY)){
			if(mapBean.getArrPoint()[newX-1][newY]!=MapInfoType.FLOOR.getIndex()){
				return;
			}else{
				pointCanCorridors.add(new PointBean(newX-1,newY,MapInfoType.CORRIDOR.getIndex()));
			}
		}
		if(newY-1>=0&&!checkIsOldPoint(oldX,oldY,newX,newY-1)){
			if(mapBean.getArrPoint()[newX][newY-1]!=MapInfoType.FLOOR.getIndex()){
				return;
			}else{
				pointCanCorridors.add(new PointBean(newX,newY-1,MapInfoType.CORRIDOR.getIndex()));
			}
		}
		if(newX+1<maxXsize&&!checkIsOldPoint(oldX,oldY,newX+1,newY)){
			if(mapBean.getArrPoint()[newX+1][newY]!=MapInfoType.FLOOR.getIndex()){
				return;
			}else{
				pointCanCorridors.add(new PointBean(newX+1,newY,MapInfoType.CORRIDOR.getIndex()));
			}
		}
		if(newY+1<maxYsize&&!checkIsOldPoint(oldX,oldY,newX,newY+1)){
			if(mapBean.getArrPoint()[newX][newY+1]!=MapInfoType.FLOOR.getIndex()){
				return;
			}else{
				pointCanCorridors.add(new PointBean(newX,newY+1,MapInfoType.CORRIDOR.getIndex()));
			}
		}
		//如果可以添加就把点画好为隧道
		mapBean.getArrPoint()[newX][newY]=MapInfoType.CORRIDOR.getIndex();
		//以这个店为开始点继续扩展可能的点
		for(PointBean pointBean:pointCanCorridors){
			floodFill4(newX,newY,pointBean.getX(),pointBean.getY());
		}
	}
	
	/**
	 * 判断是不是原始点
	 * @param oldX
	 * @param oldY
	 * @param newX
	 * @param newY
	 * @return
	 */
	private boolean checkIsOldPoint(int oldX,int oldY,int newX,int newY){
		if(oldX==newX&&oldY==newY) return true;
		return false;
	}
	
	/**
	 * 打印地图
	 */
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
