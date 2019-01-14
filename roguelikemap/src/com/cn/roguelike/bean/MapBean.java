package com.cn.roguelike.bean;

import java.util.ArrayList;
import java.util.List;

import com.cn.roguelike.control.rogueLikeEum.MapInfoType;

/**
 * 地图实体
 * @author Administrator
 *
 */
public class MapBean {
	
	/**地图点集合信息*/
	private int[][] arrPoint;
	/** 地图中当前最大房间id*/
	private int maxRoomId=100000;
	/** 房屋集合信息*/
	private List<RoomBean> roomList=new ArrayList<RoomBean>();
	
	public int getMaxRoomId() {
		return maxRoomId;
	}

	public List<RoomBean> getRoomList() {
		return roomList;
	}

	public MapBean() {
		arrPoint=new int[100][100];
	}
	
	/**
	 * 根据地图尺寸穿件一个地面地图
	 * @param maxXsize
	 * @param maxYsize
	 */
	public MapBean(int maxXsize,int maxYsize) {
		arrPoint=new int[maxXsize][maxYsize];
		for(int i=0;i<maxXsize;i++) {
			for(int j=0;j<maxYsize;j++) {
				//制定位置创建地面
				arrPoint[i][j]=MapInfoType.FLOOR.getIndex();
			}
		}
	}
	
	/**
	 * 制定尺寸 制定点默认类型
	 * @param maxXsize
	 * @param maxYsize
	 * @param typev
	 */
	public MapBean(int maxXsize,int maxYsize,int typev) {
		arrPoint=new int[maxXsize][maxYsize];
		for(int i=0;i<maxXsize;i++) {
			for(int j=0;j<maxYsize;j++) {
				//制定位置创建地面
				arrPoint[i][j]=typev;
			}
		}
	}

	public int[][] getArrPoint() {
		return arrPoint;
	}
	
	/**
	 * 判断指定点是否是某一个类型
	 * @param x
	 * @param y
	 * @param type
	 * @return
	 */
	public boolean checkIsByType(int x,int y,int type) {
		if(arrPoint[x][y]!=type) return false;
		return true;
	}
	
	/**
	 * 判断这个房间是否可以添加
	 * @param roomBean
	 * @return
	 */
	public boolean checkCanAddRoom(RoomBean roomBean) {
		for(PointBean pointBean:roomBean.getRoomPointList()) {
			if(!checkIsByType(pointBean.getX(),pointBean.getY(),MapInfoType.FLOOR.getIndex())) return false;
		}
		return true;
	}
	
	/**
	 * 判断这个房间是否可以添加(房间不连接)
	 * @param roomBean
	 * @return
	 */
	public boolean checkCanAddRoomNoConnect(RoomBean roomBean,int maxXsize,int maxYsize) {
		for(PointBean pointBean:roomBean.getRoomPointList()) {
			if(!checkIsByType(pointBean.getX(),pointBean.getY(),MapInfoType.FLOOR.getIndex())) return false;
			//判断4个点都没有房间
			if(pointBean.getX()>0)
				if(!checkIsByType(pointBean.getX()-1,pointBean.getY(),MapInfoType.FLOOR.getIndex())) return false;
			if(pointBean.getY()>0)
				if(!checkIsByType(pointBean.getX(),pointBean.getY()-1,MapInfoType.FLOOR.getIndex())) return false;
			if(pointBean.getX()<maxXsize-1)
				if(!checkIsByType(pointBean.getX()+1,pointBean.getY(),MapInfoType.FLOOR.getIndex())) return false;
			if(pointBean.getY()<maxYsize-1)
				if(!checkIsByType(pointBean.getX(),pointBean.getY()+1,MapInfoType.FLOOR.getIndex())) return false;
		}
		return true;
	}
	
	/**
	 * 添加一个房间
	 * @param roomBean
	 * @return
	 */
	public boolean addRoomToMap(RoomBean roomBean) {
		roomList.add(roomBean);
		for(PointBean pointBean:roomBean.getRoomPointList()) {
			arrPoint[pointBean.getX()][pointBean.getY()]=pointBean.getTypeV();
		}
		return true;
	}
	
	public void setRoomLinkPoint(){
		//循环房间根据房间设置
		
	}
}
