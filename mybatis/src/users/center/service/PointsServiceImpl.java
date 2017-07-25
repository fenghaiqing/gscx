package users.center.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import users.center.dao.PointsDao;
import users.center.entity.Points;

@Service
public class PointsServiceImpl implements PointsService {

	@Autowired
	private PointsDao PointsDao;
	
	@Override
	public List<Points> selectByDate(String date) {
		return PointsDao.selectByDate(date);
	}

	@Override
	public Double selectRant(String date) {
		
		return PointsDao.selectRant(date);
	}

	@Override
	public List<Map<String, Object>> selectByyear(String date) {
		
		return PointsDao.selectByyear(date);
	}

}
