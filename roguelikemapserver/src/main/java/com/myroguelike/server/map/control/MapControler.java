package com.myroguelike.server.map.control;

import java.util.ArrayList;
import java.util.List;

import com.myroguelike.server.map.bean.MapBean;
import com.myroguelike.server.map.bean.PointBean;
import com.myroguelike.server.map.bean.RoomBean;
import com.myroguelike.server.map.control.rogueLikeEum.MapInfoType;
import com.myroguelike.server.map.util.LLAssert;
import com.myroguelike.server.map.util.LLMathHelper;
public class MapControler {
	/** 地图X最大值*/
	private int maxXsize=50;
	
	/** 地图Y最大值*/
	private int maxYsize=100;
	
	/** 地图最大房间数量*/
	private int maxRoomNum=400;
	
	/** 地图最大房间链接数量*/
	private int maxRoomLinkNum=2;
	
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

	public int getMaxRoomLinkNum() {
		return maxRoomLinkNum;
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
			roomBean.creatRoom(mapBean.getMaxRoomId(), maxXsize, maxYsize);
			if(!mapBean.checkCanAddRoomNoConnect(roomBean, maxXsize, maxYsize)) continue;
			roomBean.setRoomId(mapBean.getMaxRoomId()+i);
			mapBean.addRoomToMap(roomBean);
		}
	}
	
	public void creatCorridor(){
		//循环获取一个初始点，周围两格子没有其他物体,地图边缘两格不用作道路
		for(int i=0;i<maxXsize;i++) {
			if(i<2) continue;
			if(i>maxXsize-3) continue;
			for(int j=0;j<maxYsize;j++) {
				if(j<2) continue;
				if(j>maxYsize-3) continue;
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
	 * 添加链接房间的点
	 */
	public void setRoomLinkPoint(){
		//循环房间根据房间设置
		for(RoomBean roomBean:mapBean.getRoomList()){
			//循环房间的边缘点
			for(PointBean edgePoint:roomBean.getEdgePointList()){
				//判断边缘点周边的点是否为地面,如果是地图边缘就跳过
				if(edgePoint.getX()-1>0){
					if(mapBean.getArrPoint()[edgePoint.getX()-1][edgePoint.getY()]==MapInfoType.FLOOR.getIndex()
							&&mapBean.getArrPoint()[edgePoint.getX()-2][edgePoint.getY()]==MapInfoType.CORRIDOR.getIndex()){
						roomBean.getLinkPointList().add(new PointBean(edgePoint.getX()-1,edgePoint.getY()));
					}	
				}
				if(edgePoint.getX()+1<maxXsize-1){
					if(mapBean.getArrPoint()[edgePoint.getX()+1][edgePoint.getY()]==MapInfoType.FLOOR.getIndex()
							&&mapBean.getArrPoint()[edgePoint.getX()+2][edgePoint.getY()]==MapInfoType.CORRIDOR.getIndex()){
						roomBean.getLinkPointList().add(new PointBean(edgePoint.getX()+1,edgePoint.getY()));
					}	
				}
				if(edgePoint.getY()-1>0){
					if(mapBean.getArrPoint()[edgePoint.getX()][edgePoint.getY()-1]==MapInfoType.FLOOR.getIndex()
							&&mapBean.getArrPoint()[edgePoint.getX()][edgePoint.getY()-2]==MapInfoType.CORRIDOR.getIndex()){
						roomBean.getLinkPointList().add(new PointBean(edgePoint.getX(),edgePoint.getY()-1));
					}	
				}
				if(edgePoint.getY()+1<maxYsize-1){
					if(mapBean.getArrPoint()[edgePoint.getX()][edgePoint.getY()+1]==MapInfoType.FLOOR.getIndex()
							&&mapBean.getArrPoint()[edgePoint.getX()][edgePoint.getY()+2]==MapInfoType.CORRIDOR.getIndex()){
						roomBean.getLinkPointList().add(new PointBean(edgePoint.getX(),edgePoint.getY()+1));
					}	
				}
			}
			
			//随机这个房间的链接点为门
			if(roomBean.getLinkPointList().size()>0){
				PointBean linkPoint=null;
				for(int i=0;i<maxRoomLinkNum;i++){
					int index=LLMathHelper.random(0, roomBean.getLinkPointList().size()-1);
					linkPoint=roomBean.getLinkPointList().get(index);
					mapBean.getArrPoint()[linkPoint.getX()][linkPoint.getY()]=MapInfoType.DOOR.getIndex();
				}
			}
		}
	}

	public void corridorRollBack() {
		//循环所有的通道点
		for(int i=0;i<maxXsize;i++) {
			for(int j=0;j<maxYsize;j++) {
				if(mapBean.getArrPoint()[i][j]!=MapInfoType.CORRIDOR.getIndex()) continue;
				floodFill4Back(i,j);
			}
		}
	}
	
	private void floodFill4Back(int newX,int newY){
		int count=0;
		int tmpx,tmpy;
		tmpx=tmpy=-1;
		if(newX>0) {
			if(mapBean.getArrPoint()[newX-1][newY]==MapInfoType.CORRIDOR.getIndex()||mapBean.getArrPoint()[newX-1][newY]==MapInfoType.DOOR.getIndex()) {
				count++;
				tmpx=newX-1;
				tmpy=newY;
			}
		}
		if(newX<maxXsize-1) {
			if(mapBean.getArrPoint()[newX+1][newY]==MapInfoType.CORRIDOR.getIndex()||mapBean.getArrPoint()[newX+1][newY]==MapInfoType.DOOR.getIndex()) {
				count++;
				tmpx=newX+1;
				tmpy=newY;
			}
		}
		if(newY>0) {
			if(mapBean.getArrPoint()[newX][newY-1]==MapInfoType.CORRIDOR.getIndex()||mapBean.getArrPoint()[newX][newY-1]==MapInfoType.DOOR.getIndex()) {
				count++;
				tmpx=newX;
				tmpy=newY-1;
			}
		}
		if(newY<maxYsize-1) {
			if(mapBean.getArrPoint()[newX][newY+1]==MapInfoType.CORRIDOR.getIndex()||mapBean.getArrPoint()[newX][newY+1]==MapInfoType.DOOR.getIndex()) {
				count++;
				tmpx=newX;
				tmpy=newY+1;
			}
		}
		//如果没有有两个点不是地面那么说明这个点是死胡同
		if(count<=1) {
			mapBean.getArrPoint()[newX][newY]=MapInfoType.FLOOR.getIndex();
			if(tmpx>=0&&tmpy>=0) floodFill4Back(tmpx,tmpy);
		}
	}
	
	/**
	 * 打印地图
	 */
	public String printMapInfo() {
		LLAssert.isTrue(mapBean!=null,"map is null!");
		LLAssert.isTrue(mapBean.getArrPoint().length>0,"map is empty!");
		System.out.println("room num is "+mapBean.getRoomList().size()+"*==============================*");
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<maxXsize;i++) {
			for(int j=0;j<maxYsize;j++) {
				if(mapBean.getArrPoint()[i][j]==MapInfoType.CORRIDOR.getIndex())
					sb.append("#");
				else if(mapBean.getArrPoint()[i][j]==MapInfoType.DOOR.getIndex())
					sb.append("W");
				else
					sb.append(String.valueOf(mapBean.getArrPoint()[i][j]));
			}
			sb.append("\r\n");
		}
		System.out.println(sb.toString());
		return sb.toString();
	}
	
	public List<int[]> getMapInfo(){
		List<int[]> listInfo=new ArrayList<int[]>();
		for(int i=0;i<maxXsize;i++) {
			listInfo.add(mapBean.getArrPoint()[i]);
		}
		return listInfo;
	}
	
//	public List<Integer> getMapInfo(){
//		List<Integer> listInfo=new ArrayList<Integer>();
//		for(int i=0;i<maxXsize;i++) {
//			listInfo.add(i);
//		}
//		return listInfo;
//	}
}
