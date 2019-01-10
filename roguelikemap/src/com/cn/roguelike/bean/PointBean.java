package com.cn.roguelike.bean;

import com.cn.roguelike.control.rogueLikeEum.MapInfoType;

public class PointBean {
	/** 点x值*/
	private int x;
	/** 点y值*/
	private int y;
	/** 该点类型值*/
	private int typeV;
	
	/**
	 * 默认创建一个0，0位置的地面点
	 */
	public PointBean(){
		this.x=0;
		this.y=0;
		this.typeV=MapInfoType.FLOOR.getIndex();
	}
	
	/**
	 * 默认创建地面在指定的点上
	 * @param x
	 * @param y
	 */
	public PointBean(int x,int y){
		this.x=x;
		this.y=y;
		this.typeV=MapInfoType.FLOOR.getIndex();
	}
	
	/**
	 * 根据类型创建一个点
	 * @param x
	 * @param y
	 * @param typeV
	 */
	public PointBean(int x,int y,int typeV){
		this.x=x;
		this.y=y;
		this.typeV=typeV;
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
	public int getTypeV() {
		return typeV;
	}
	public void setTypeV(int typeV) {
		this.typeV = typeV;
	}
	
}
