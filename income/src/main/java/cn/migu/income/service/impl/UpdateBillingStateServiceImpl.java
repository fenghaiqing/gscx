package cn.migu.income.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSON;

import cn.migu.income.dao.MiguIncomeBillMapper;
import cn.migu.income.dao.MiguIncomeManagerMapper;
import cn.migu.income.dao.TMiguIncomeBillingMapper;
import cn.migu.income.pojo.MiguIncomeBill;
import cn.migu.income.pojo.MiguIncomeManager;
import cn.migu.income.pojo.TMiguIncomeBilling;
import cn.migu.income.service.UpdateBillingStateService;
import cn.migu.income.util.MiguConstants;
import cn.migu.income.util.PropValue;
import cn.migu.income.webservices.GetTaxInvoiceInfo;
import cn.migu.income.webservices.GetTaxInvoiceInfoResponse;
import cn.migu.income.webservices.SynTaxInvoiceInfoSrv;
import cn.migu.income.webservices.SynTaxInvoiceInfoSrvStub;

@Service
public class UpdateBillingStateServiceImpl implements UpdateBillingStateService {

	@Autowired
	private TMiguIncomeBillingMapper incomeBillingMapper;

	private SynTaxInvoiceInfoSrv synTaxInvoiceInfoSrv;
	
	@Autowired
	private MiguIncomeBillMapper incomeBillMapper;

	final static Logger log = LoggerFactory.getLogger(UpdateBillingStateServiceImpl.class);

	@Override
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED)
	public void updateState() {
		// 查询所有已经申请开票的数据
		List<TMiguIncomeBilling> list = incomeBillingMapper.selectUnBillSuccess();
		log.info("申请开票的数据：总数=" + list.size());
		// 遍历数据，调用webservice
		try {
			log.info("开始调用webservice接口，接口地址:" + MiguConstants.BILL_WEBSERVICE_LOCATION);
			synTaxInvoiceInfoSrv = new SynTaxInvoiceInfoSrvStub(PropValue.getPropValue(MiguConstants.BILL_WEBSERVICE_LOCATION));
			GetTaxInvoiceInfo getTaxInvoiceInfo = new GetTaxInvoiceInfo();
			if (list.size() > 0) {
				for (TMiguIncomeBilling incomeBilling : list) {
					log.info("开始执行,项目号:"+incomeBilling.getProjectId()+",priKey=" + incomeBilling.getBillingKey());
					String priKey = incomeBilling.getBillingKey();
					String json = "{\"systemSource\":\"income\",\"priKey\":" + priKey + "}";

					getTaxInvoiceInfo.setArgs0(json);
					GetTaxInvoiceInfoResponse getTaxInvoiceInfoResponse = synTaxInvoiceInfoSrv
							.getTaxInvoiceInfo(getTaxInvoiceInfo);
					String result = getTaxInvoiceInfoResponse.get_return();
					log.info("发票申请查询返回信息："+result);
					// 解析返回值，获取开票时间，开票状态
					Map<String, Object> map = (Map<String, Object>) JSON.parse(result);
					
					Object status = map.get("returnFlag");
					
					if (status != null && "Y".equals(status.toString())) {
						if(map.get("taxList")!=null){
							
							// 更新数据库开票时间，开票状态
							List<Map<String, String>> taxList = (List<Map<String, String>>) map.get("taxList");
							
							Map<String, String> resultMap = taxList.get(0);
							
							incomeBilling.setBillingStatus(resultMap.get("fpzt"));
							
							incomeBilling.setBillingTime(resultMap.get("kprq")==null?"":resultMap.get("kprq"));
						
							incomeBillingMapper.updateInvoiceStatus(incomeBilling);
							
							//删除开票明细信息
							int i = incomeBillMapper.deleteIncomeBill(incomeBilling.getBillingKey());
							if(i>=0){
								for (int j = 0; j < taxList.size(); j++) {
									Map<String, String> returnedData = taxList.get(j);
									MiguIncomeBill bill = new MiguIncomeBill();
									bill=createIncomeBill(incomeBilling, returnedData, bill);
									incomeBillMapper.insertSelective(bill);
								}
							}
							
							/*//同步开票信息到两非
							MiguIncomeBill bill = null;
							bill = incomeBillMapper.selectOneByMarnagerId(incomeBilling.getBillingKey());
							if(bill!=null){
								bill=createIncomeBill(incomeBilling, resultMap, bill);
								incomeBillMapper.updateIncomeBill(bill);
							}else{
								bill=new MiguIncomeBill();
								bill=createIncomeBill(incomeBilling, resultMap, bill);
								incomeBillMapper.insertSelective(bill);
							}*/
						}
					}
				}
			}
		  } catch (Exception e) {
			  e.printStackTrace();
			  log.error("同步开票信息出错："+e.getMessage());
			 TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			 
		}

	}

	private MiguIncomeBill createIncomeBill(TMiguIncomeBilling incomeBilling,Map<String, String> resultMap,MiguIncomeBill bill){
		bill.setProjectId(incomeBilling.getProjectId());
		bill.setBillingKey(incomeBilling.getBillingKey());
		bill.setInvoiceCode(resultMap.get("fpdm"));
		bill.setInvoiceNumber(resultMap.get("fphm"));
		bill.setBillingDate(resultMap.get("kprq"));
		bill.setPurchaseUnitName(resultMap.get("ghdwmc"));
		bill.setPurchaseUnitCode(resultMap.get("ghdwdm"));
		bill.setPurchaseUnitAddr(resultMap.get("ghdwdzdh"));
		bill.setPurchaseUnitAccount(resultMap.get("ghdwyhzh"));
		bill.setPinUnitName(resultMap.get("xhdwmc"));
		bill.setPinUnitCode(resultMap.get("xhdwdm"));
		bill.setPinUnitAddr(resultMap.get("xhdwdzdh"));
		bill.setPinUnitAccount(resultMap.get("xhdwyhzh"));
		bill.setPrice(resultMap.get("hjje")==null?null:Double.parseDouble(resultMap.get("hjje")));
		bill.setTax(resultMap.get("se")==null?null:Double.parseDouble(resultMap.get("se")));
		bill.setTotal(resultMap.get("jshj")==null?null:Double.parseDouble(resultMap.get("jshj")));
		bill.setPayee(resultMap.get("skr"));
		bill.setReviewer(resultMap.get("fhr"));
		bill.setDrawer(resultMap.get("kpr"));
		bill.setInvoiceStatus(resultMap.get("fpzt"));
		bill.setBillingType(resultMap.get("kplx"));
		return bill;
	}
}
