package com.gscx.ssm.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gscx.ssm.pojo.MiguIncomeDetail;
import com.gscx.ssm.service.MiguIncomeDetailService;

@RestController
@RequestMapping(value="/MiguIncomeDetailCtrl")
public class MiguIncomeDetailCtrl {

	@Autowired
	private MiguIncomeDetailService miguIncomeDetailService;
	
	@RequestMapping(value="/selectByPrimaryKey",method=RequestMethod.GET)
	@ResponseBody
	 public MiguIncomeDetail selectByExample(){
		 return miguIncomeDetailService.selectByExample();
	 }
}
