package cn.migu.income.util;


import cn.migu.income.webservices.ContractSyncService;
import cn.migu.income.webservices.ContractSyncServiceStub;
import cn.migu.income.webservices.GetContractList;
import cn.migu.income.webservices.GetContractListResponse;
import cn.migu.income.webservices.GetTaxInvoiceInfo;
import cn.migu.income.webservices.GetTaxInvoiceInfoResponse;
import cn.migu.income.webservices.SynTaxInvoiceInfoSrv;
import cn.migu.income.webservices.SynTaxInvoiceInfoSrvStub;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class WebservicesUtil {

	public static void main(String[] args) throws Exception {
//		ContractSyncService contractSyncService = new ContractSyncServiceStub(
//				"http://117.185.122.252:38080/contract-web/services/contractSyncService.contractSyncServiceHttpSoap12Endpoint/");
//
//		GetContractList getContractList = new GetContractList();
//		getContractList.setBeginDate("2017-01-03 15:27:29");
//		getContractList.setEndDate("2017-06-15 14:23:29");
//		getContractList.setCurrentPageP("1");
//		getContractList.setPageSizeP("20");
//		// getContractList.setTotalPage("");
//		// getContractList.setTotalRecord("");
//		// getContractList.setContractNumber("");
//		// getContractList.setContractCode("");
//		GetContractListResponse GetContractListResponse = contractSyncService.getContractList(getContractList);
//		System.out.println(GetContractListResponse.get_return());
//
//		String json = GetContractListResponse.get_return();
//
//		JSONObject dataJson = JSONObject.fromObject(json);
//		JSONObject data = dataJson.getJSONObject("data");
//		JSONArray contractList = data.getJSONArray("contractList");
//		System.out.println(contractList.size());
//		for (int i = 0; i < contractList.size(); i++) {
//			System.out.println("---------------");
//			JSONObject contract = (JSONObject) contractList.get(i);
//			System.out.println("conNo=" + contract.get("conNo"));
//			JSONArray contractVendorList = contract.getJSONArray("contractVendorList");
//			for (int j = 0; j < contractVendorList.size(); j++) {
//				JSONObject contractVendor = (JSONObject) contractVendorList.get(j);
//				System.out.println("conVendorId=" + contractVendor.get("conVendorId"));
//			}
//		}
		
		
		
		SynTaxInvoiceInfoSrv synTaxInvoiceInfoSrv =  new SynTaxInvoiceInfoSrvStub("http://117.185.122.246:80/services/SynTaxInvoiceInfoSrv.SynTaxInvoiceInfoSrvHttpSoap12Endpoint/");
		GetTaxInvoiceInfo getTaxInvoiceInfo = new GetTaxInvoiceInfo();
		
		String json = "{\"systemSource\":\"income\",\"priKey\":\"0361421502432427927\"}";
		
		getTaxInvoiceInfo.setArgs0(json);
		GetTaxInvoiceInfoResponse getTaxInvoiceInfoResponse = 
				synTaxInvoiceInfoSrv.getTaxInvoiceInfo(getTaxInvoiceInfo);
		
		String result = getTaxInvoiceInfoResponse.get_return();
		
		
	}
}
