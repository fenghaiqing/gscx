package cn.migu.income.service;

import java.util.List;

import cn.migu.income.pojo.TMiguContract;
import net.sf.json.JSONArray;

public interface IMiguContractManageService
{
	/**
	 * 查询合同
	 * @param contractList
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	List<TMiguContract> queryAllContract(JSONArray contractList) throws Exception;
	
	/**
	 * 新增合同
	 * @param contracts
	 * @return
	 */
	String doAssociatedContract(List<TMiguContract> contracts) throws Exception;
	
	/**
	 * 查询已关联合同总数
	 * @param projectId
	 * @param q_conNumber
	 * @param q_conNo
	 * @param q_conStartTime
	 * @param q_conEndTime
	 * @return
	 */
	int queryTotal(String projectId, String q_conNumber, String q_conNo);
	
	/**
	 * 查询已关联合同
	 * @param projectId
	 * @param q_conNumber
	 * @param q_conNo
	 * @param q_conStartTime
	 * @param q_conEndTime
	 * @param parseInt
	 * @param parseInt2
	 * @return
	 */
	List<TMiguContract> queryAllAssociatedContract(String projectId, String q_conNumber, String q_conNo,
			int page, int pageSize);
	
	/**
	 * 解除合同关联
	 * @param list
	 * @return
	 */
	String cancelAssociatedContract(List<TMiguContract> list);
	
   
}
