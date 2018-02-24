package cn.migu.income.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import cn.migu.income.dao.TMiguContractMapper;
import cn.migu.income.pojo.TMiguContract;
import cn.migu.income.service.IMiguContractManageService;
import cn.migu.income.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



@Service
public class MiguContractManageServiceImpl implements IMiguContractManageService
{
    final static Logger log = LoggerFactory.getLogger(IMiguContractManageService.class);
    
    @Autowired
    private TMiguContractMapper tMiguContractMapper;

	@Override
	public List<TMiguContract> queryAllContract(JSONArray contractList) throws Exception {
		List<TMiguContract> list = new ArrayList<TMiguContract>();
		for (int i = 0; i < contractList.size(); i++) {
			TMiguContract tMiguContract = new TMiguContract();
			JSONObject contract = (JSONObject) contractList.get(i);
			tMiguContract.setConId(StringUtil.nullToBlank(contract.getString("conId")));
			tMiguContract.setConProjectContrctor(StringUtil.nullToBlank(contract.getString("conProjectContrctor")));
			tMiguContract.setConUndertakeDeptname(StringUtil.nullToBlank(contract.getString("conUndertakeDeptname")));
			if(contract.getString("conNumber")==null){
				
			}
			tMiguContract.setConNumber(new BigDecimal(contract.getString("conNumber")));
			tMiguContract.setConNo(StringUtil.nullToBlank(contract.getString("conNo")));
			tMiguContract.setConName(StringUtil.nullToBlank(contract.getString("conName")));
			tMiguContract.setConBigType(StringUtil.nullToBlank(contract.getString("conBigType")));
			tMiguContract.setConStartTime(StringUtil.nullToBlank(contract.getString("conStartTime")));
			tMiguContract.setConEndTime(StringUtil.nullToBlank(contract.getString("conEndTime")));
			tMiguContract.setConTypePayrec(StringUtil.nullToBlank(contract.getString("conTypePayrec")));
			tMiguContract.setConCurrencyAmount(new BigDecimal(contract.getString("conCurrencyAmount")));
			tMiguContract.setConCapitalNatureName(StringUtil.nullToBlank(contract.getString("conCapitalNatureName")));
			
			JSONArray contractVendorList = contract.getJSONArray("contractVendorList");
			for (int j = 0; j < contractVendorList.size(); j++) {
				JSONObject contractVendor = (JSONObject) contractVendorList.get(j);
				tMiguContract.setConVendorId(StringUtil.nullToBlank(contractVendor.getString("conVendorId")));
				tMiguContract.setConVendorName(StringUtil.nullToBlank(contractVendor.getString("conVendorName")));
				tMiguContract.setConBankAbstrct(StringUtil.nullToBlank(contractVendor.getString("conBankAbstrct")));
				tMiguContract.setConBankAccountNum(StringUtil.nullToBlank(contractVendor.getString("conBankAccountNum")));
				tMiguContract.setConTaxQualified(StringUtil.nullToBlank(contractVendor.getString("conTaxQualified")));
				if(contractVendor.getString("conTaxamount")!=null&&!contractVendor.getString("conTaxamount").equals("")){
					tMiguContract.setConTaxamount(new BigDecimal(contractVendor.getString("conTaxamount")));
				}else{
					tMiguContract.setConTaxamount(new BigDecimal(0));
				}
				
			}
//			conId-ConId-class java.lang.String
//			projectId-ProjectId-class java.lang.String
			list.add(tMiguContract);
		}
		return list;
	}

	@Override
	public String doAssociatedContract(List<TMiguContract> contracts) {
		String successResult = "";
		String failResult = "";
		for (TMiguContract tMiguContract : contracts) {
			int count = tMiguContractMapper.checkDataExists(tMiguContract);
			if(count==0){
				int i = tMiguContractMapper.saveContract(tMiguContract);
				if(i==1){
					successResult = successResult+tMiguContract.getConNo()+"、";
				}
			}else{
				failResult = failResult+tMiguContract.getConNo()+"、";
			}
		}
		if(!successResult.equals("")){
			successResult = successResult.substring(0, successResult.length()-1)+"合同绑定成功。";
		}
		if(!failResult.equals("")){
			failResult = failResult.substring(0, failResult.length()-1)+"合同已与本项目绑定，本次绑定失败。";
		}
		String result = failResult+"<br/>"+successResult;
		return result;
	}

	@Override
	public int queryTotal(String projectId, String q_conNumber, String q_conNo) {
		Map<String, Object> param = new HashMap<String, Object>();
		if (!"".equals(projectId) && projectId != null) {
			param.put("projectId", projectId);
		}
		if (!"".equals(q_conNumber) && q_conNumber != null) {
			param.put("q_conNumber", q_conNumber);
		}
		if (!"".equals(q_conNo) && q_conNo != null) {
			param.put("q_conNo", q_conNo);
		}
		return tMiguContractMapper.queryTotal(param);
	}

	@Override
	public List<TMiguContract> queryAllAssociatedContract(String projectId, String q_conNumber, String q_conNo, int page, int pageSize) {
		Map<String, Object> param = new HashMap<String, Object>();
		if (!"".equals(projectId) && projectId != null) {
			param.put("projectId", projectId);
		}
		if (!"".equals(q_conNumber) && q_conNumber != null) {
			param.put("q_conNumber", q_conNumber);
		}
		if (!"".equals(q_conNo) && q_conNo != null) {
			param.put("q_conNo", q_conNo);
		}
		
		param.put("curPage", page);
		param.put("pageSize", pageSize);
		List<TMiguContract> list = tMiguContractMapper.queryAllAssociatedContract(param);
		return list;
	}

	@Override
	public String cancelAssociatedContract(List<TMiguContract> list) {
		String result = "0";
		int deleteCount = tMiguContractMapper.cancelAssociatedContract(list);
		if (deleteCount <= 0) {
			return "-1";
		}
		return result;
	}
}
