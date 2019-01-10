package com.cn.roguelike.bean;

import com.cn.roguelike.control.rogueLikeEum.MapInfoType;

/**
 * 地图实体
 * @author Administrator
 *
 */
public class MapBean {
	
	/**地图点集合信息*/
	private int[][] arrPoint;
	
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
	
	
}
