package users.center.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import users.center.entity.Points;
import users.center.service.PointsService;


@RestController
@RequestMapping(value="/queryRant")
public class CrudController {
	
	@Autowired
	private PointsService pointsService;
	
	@RequestMapping(value="/selectByDate",method=RequestMethod.GET)
	public List<Points> selectByDate(String date){
		return pointsService.selectByDate(date);
	}
	
	@RequestMapping(value="/selectByear",method=RequestMethod.GET)
	public List<Map<String, Object>> selectByear(String date){
		return pointsService.selectByyear(date);
	}
	
	@RequestMapping(value="/selectRant",method=RequestMethod.GET)
	public Map<String, Object> selectRant(String date){
		String month = date.substring(date.lastIndexOf("-")+1);
		String year = date.substring(0, 4);
		String preMonth=year+"-"+(Integer.parseInt(month)-1);
		String lastYearMonth=(Integer.parseInt(year)-1)+"-"+month;
		Double thisrant = pointsService.selectRant(date);
		Double prerant = pointsService.selectRant(preMonth);
		Double lastrant = pointsService.selectRant(lastYearMonth);
		Map<String, Object> map =new HashMap<>();
		map.put("thisrant", thisrant);
		map.put("prerant", prerant);
		map.put("lastrant", lastrant);
		return map;
	}
}