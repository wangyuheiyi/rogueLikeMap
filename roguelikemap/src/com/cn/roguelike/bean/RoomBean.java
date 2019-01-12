package com.cn.roguelike.bean;
import java.util.ArrayList;
import java.util.List;

import com.cn.roguelike.control.rogueLikeEum.MapInfoType;
import com.cn.roguelike.control.rogueLikeEum.RoomType;
import com.cn.roguelike.util.LLMathHelper;

/**
 * 房间实体
 * @author Administrator
 *
 */
public class RoomBean {
	/** 房间id*/
	private int roomId;
	/** 房间最小边长*/
	private int minRoomSide=5;
	/** 房间最大边长*/
	private int maxRoomSide=10;
	/** 点x值*/
	private int x;
	/** 点y值*/
	private int y;
	/** 房间大小(点的总个数)*/
	private int roomsize;
	/**房间所在点*/
	private List<PointBean> roomPointList=new ArrayList<PointBean>();
	
	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	public int getMinRoomSide() {
		return minRoomSide;
	}
	public void setMinRoomSide(int minRoomSide) {
		this.minRoomSide = minRoomSide;
	}
	public int getMaxRoomSide() {
		return maxRoomSide;
	}
	public void setMaxRoomSide(int maxRoomSide) {
		this.maxRoomSide = maxRoomSide;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getRoomsize() {
		return roomsize;
	}
	
	
	public List<PointBean> getRoomPointList() {
		return roomPointList;
	}
	
//	private void addPointToList(int xv,int yv,int typeValue) {
//		//初始化所有的房间点
//		arrPoint=new int[xSideLen][ySideLen];
//		for(int i=0;i<xSideLen;i++) {
//			for(int j=0;j<ySideLen;j++) {
//				arrPoint[x+i][y+j]=typeValue;
//			}
//		}
//	}
	
	public void creatRoom(int id,int xRange,int yRange){
		this.roomId=id;
		//随机一种房屋类型
		int rroomType=LLMathHelper.random(RoomType.SQUARE.getIndex(), RoomType.values().length);
		RoomType roomType=RoomType.valueOf(rroomType);
		switch (roomType) {
		case SQUARE:
			creatSquareRoom(xRange,yRange);
			break;
		case CIRCULAR:
			creatCircularRoom(xRange,yRange);
			break;
		default:
			creatSquareRoom(xRange,yRange);
			break;
		}
	}
	
	/**
	 * 创建正方型房间
	 */
	private void creatSquareRoom(int xRange,int yRange){
		//随机一个原点
		this.x=LLMathHelper.random(0, xRange-maxRoomSide);
		this.y=LLMathHelper.random(0, yRange-maxRoomSide);
		//随机画板的x轴边长
		int xSideLen=LLMathHelper.random(minRoomSide, maxRoomSide);
		//随机画板的y轴边长
		int ySideLen=LLMathHelper.random(minRoomSide, maxRoomSide);
		//正方型就将所有的房间填满
		roomsize=xSideLen*ySideLen;
		for(int i=0;i<xSideLen;i++) {
			for(int j=0;j<ySideLen;j++) {
				roomPointList.add(new PointBean(x+i,y+j,MapInfoType.ROOM.getIndex()));
			}
		}
	}
	
	private void creatCircularRoom(int xRange,int yRange) {
		//随机一个圆心
		int xCircle=LLMathHelper.random(0+maxRoomSide, xRange-maxRoomSide);
		int YCircle=LLMathHelper.random(0+maxRoomSide, yRange-maxRoomSide);
		//随机一个半径
		int radius=LLMathHelper.random(minRoomSide, maxRoomSide);
		//设置远点
		this.x=xCircle-radius;
		if(this.x<0) this.x=0;
		this.y=YCircle-radius;
		if(this.y<0) this.y=0;
		//生成中轴线
		for(int rad=0;rad<radius*2+1;rad++) {
			roomPointList.add(new PointBean(x+rad,YCircle,MapInfoType.ROOM.getIndex()));
			roomPointList.add(new PointBean(xCircle,y+rad,MapInfoType.ROOM.getIndex()));
			roomsize++;
		}
		//循环生成原房间上半部分
		int ysize=1;
		for(int i=0;i<radius;i++) {
			drawLeftPoint(x+i,YCircle,ysize,MapInfoType.ROOM.getIndex());
			drawRightPoint(x+i,YCircle,ysize,MapInfoType.ROOM.getIndex());
			ysize++;
			roomsize++;
		}
		//循环生成原房间下半部分
		ysize=1;
		for(int i=radius*2;i>radius;i--) {
			drawLeftPoint(x+i,YCircle,ysize,MapInfoType.ROOM.getIndex());
			drawRightPoint(x+i,YCircle,ysize,MapInfoType.ROOM.getIndex());
			ysize++;
			roomsize++;
		}
	}
	
	private void drawLeftPoint(int x,int YCircle,int ysize,int type) {
		for(int j=ysize;j>=1;j--) {
			roomPointList.add(new PointBean(x,YCircle-j,type));
		}
	}
	
	private void drawRightPoint(int x,int YCircle,int ysize,int type) {
		for(int j=1;j<=ysize;j++) {
			roomPointList.add(new PointBean(x,YCircle+j,type));
		}
	}
}
