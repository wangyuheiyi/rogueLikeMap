package com.cn.roguelike.bean;
/**
 * 地图实体
 * @author Administrator
 *
 */
public class MapBean {
	
	/**地图信息*/
	private int[][] arrMap;
	
	public MapBean() {
		arrMap=new int[100][100];
	}
	
	public MapBean(int maxXsize,int maxYsize) {
		arrMap=new int[maxXsize][maxYsize];
	}

	public int[][] getArrMap() {
		return arrMap;
	}
	
}
