package com.cn.roguelike.control;

import java.util.List;

import com.cn.roguelike.util.LLIndexedEnum;

public class rogueLikeEum {
	/**
	 * 模块的英雄技能
	 */
	public static enum MapInfoType implements LLIndexedEnum 
	{
		/**地板**/
		FLOOR(0),
		/**房间**/
		 ROOM(1),
		/**走廊**/
		 CORRIDOR(2),
		 /**墙**/
		 WALL(3),
		;
		
		private final int index;
		
		private MapInfoType(int index)
		{
			this.index = index;
		}
		@Override
		public int getIndex() 
		{
			return index;
		}
		
		private static final List<MapInfoType> values = IndexedEnumUtil.toIndexes(MapInfoType.values());

		public static MapInfoType valueOf(int index)
		{
			return LLIndexedEnum.IndexedEnumUtil.valueOf(values, index);
		}
	}
}
