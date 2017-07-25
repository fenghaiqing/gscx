package users.center.service;

import java.util.List;
import java.util.Map;

import users.center.entity.Points;

public interface PointsService {

	List<Points> selectByDate(String date);
	
	Double selectRant(String date);
	
	List<Map<String, Object>> selectByyear(String date);
}
