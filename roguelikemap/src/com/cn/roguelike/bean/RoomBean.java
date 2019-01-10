package com.cn.roguelike.bean;
/**
 * 房间实体
 * @author Administrator
 *
 */
public class RoomBean {
	/** 房间id*/
	private int roomId;
	/** 点x值*/
	private int x;
	/** 点y值*/
	private int y;
	/** 房间大小(点的总个数)*/
	private int roomsize;
	/**地图信息*/
	private int[][] arrPoint;
	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getRoomsize() {
		return roomsize;
	}
	public void setRoomsize(int roomsize) {
		this.roomsize = roomsize;
	}
	public int[][] getArrPoint() {
		return arrPoint;
	}
	
	
}
