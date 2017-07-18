package com.gscx.ssm.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gscx.ssm.dao.MiguIncomeDetailMapper;
import com.gscx.ssm.pojo.MiguIncomeDetail;
import com.gscx.ssm.service.MiguIncomeDetailService;

@Service
public class MiguIncomeDetailServiceImpl implements MiguIncomeDetailService {

	@Autowired
	private MiguIncomeDetailMapper incomeDetailMapper;
	
	public MiguIncomeDetail selectByExample() {
		MiguIncomeDetail y =incomeDetailMapper.selectByPrimaryKey(BigDecimal.valueOf(104));
		return incomeDetailMapper.selectByPrimaryKey(BigDecimal.valueOf(123));
	}

}
