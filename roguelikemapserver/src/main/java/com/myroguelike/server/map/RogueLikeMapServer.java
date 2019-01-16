package com.myroguelike.server.map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.myroguelike.server.map.control.MapControler;

@RestController
@RequestMapping("/")
public class RogueLikeMapServer {
	@RequestMapping(value="/initMap",method =RequestMethod.GET)
	public ModelAndView initMap(){
		MapControler.getInstance().initMapInfo();
		ModelAndView modelAndView = new ModelAndView();	
		modelAndView.addObject("mapInfo", MapControler.getInstance().getMapInfo());
		modelAndView.setViewName("map");
		return modelAndView;
	}
	
	@RequestMapping(value="/creatRoom",method =RequestMethod.GET)
	public ModelAndView creatRoom(){
		MapControler.getInstance().creatRoomByMap();
		MapControler.getInstance().printMapInfo();
		ModelAndView modelAndView = new ModelAndView();	
		modelAndView.addObject("mapInfo", MapControler.getInstance().getMapInfo());
		modelAndView.setViewName("map");
		return modelAndView;
	}
	
	@RequestMapping(value="/creatCorridor",method =RequestMethod.GET)
	public ModelAndView creatCorridor(){
		MapControler.getInstance().creatCorridor();
		MapControler.getInstance().printMapInfo();
		ModelAndView modelAndView = new ModelAndView();	
		modelAndView.addObject("mapInfo", MapControler.getInstance().getMapInfo());
		modelAndView.setViewName("map");
		return modelAndView;
	}
	
	@RequestMapping(value="/setRoomLinkPoint",method =RequestMethod.GET)
	public ModelAndView setRoomLinkPoint(){
		MapControler.getInstance().setRoomLinkPoint();
		MapControler.getInstance().printMapInfo();
		ModelAndView modelAndView = new ModelAndView();	
		modelAndView.addObject("mapInfo", MapControler.getInstance().getMapInfo());
		modelAndView.setViewName("map");
		return modelAndView;
	}
	
	@RequestMapping(value="/corridorRollBack",method =RequestMethod.GET)
	public ModelAndView corridorRollBack(){
		MapControler.getInstance().corridorRollBack();
		MapControler.getInstance().printMapInfo();
		ModelAndView modelAndView = new ModelAndView();	
		modelAndView.addObject("mapInfo", MapControler.getInstance().getMapInfo());
		modelAndView.setViewName("map");
		return modelAndView;
	}
	
	@RequestMapping(value="/initAllMap",method =RequestMethod.GET)
	public ModelAndView initAllMap(){
		MapControler.getInstance().initMapInfo();
		MapControler.getInstance().creatRoomByMap();
		MapControler.getInstance().creatCorridor();
		MapControler.getInstance().setRoomLinkPoint();
		MapControler.getInstance().corridorRollBack();
		MapControler.getInstance().printMapInfo();
		ModelAndView modelAndView = new ModelAndView();	
		modelAndView.addObject("mapInfo", MapControler.getInstance().getMapInfo());
		modelAndView.setViewName("map");
		return modelAndView;
	}
}
