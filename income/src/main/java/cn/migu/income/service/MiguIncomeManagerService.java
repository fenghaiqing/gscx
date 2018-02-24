package cn.migu.income.service;

import cn.migu.income.pojo.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface MiguIncomeManagerService {

	Map<String, Object> queryIncomeManagerList(MiguIncomeManager incomeManager, Integer page, Integer rows);

	int queryTotal(String q_month, String q_projectId, String q_projectName, String q_estimateState,String realIncomeStatus,
			  String q_incomeClassId,
		        String q_incomeSectionId, String q_dept,String q_userName, String est_income,
		        String est_exclusive_tax, String q_cx,String q_bill,String q_merge,String bzCycleReal,MiguUsers user);


	/**
     * 查询项目信息数目-已经通过审核且需要开票的实际收入所在项目
     * @param q_projectId
     * @param q_projectName
     * @param user
     * @return
     */
    int queryDrawBillTotal_1(String q_projectId, String q_projectName, MiguUsers user);
    
	/**
     * 查询项目信息数目-已经通过审核且不需要开票的实际收入所在项目
     * @param q_projectId
     * @param q_projectName
     * @param user
     * @return
     */
    int queryNoBillTotal(String q_projectId, String q_projectName, MiguUsers user);

    /**
     * 查询开票条目数
     *
     * @param q_month_begin
     * @param q_month_end
     * @param q_projectId
     * @param q_projectName
     * @param q_billState
     * @param user
     * @return
     */
    int queryDrawBillTotal(String q_month_begin, String q_month_end, String q_projectId, String q_projectName, String q_billState,
    		String q_month_bill,String q_incomeClassId,String q_incomeSectionId,String q_dept,
    		String q_userName,String q_bill_num,String q_bill_total,MiguUsers user);

    /**
     * 查询当前用户下所有实际收入信息
     * @param q_month
     * @param q_projectId
     * @param q_projectName
	 * @param q_estimateState
	 * @param realIncomeStatus
	 * @param parseInt
	 * @param parseInt2
	 * @param user
	 * @return
	 */
	List<MiguIncomeManager> queryAllRealIncome(String q_month, String q_projectId, String q_projectName,
                                                   String q_estimateState, String realIncomeStatus,
                                                   String q_incomeClassId,
                                       	        String q_incomeSectionId, String q_dept, String q_userName,
                                       	        String est_income,String est_exclusive_tax,String q_cx,
                                       	        String q_bill,String q_merge,String bzCycleReal, int parseInt, int parseInt2, MiguUsers user);

    /**
     * 查询开票具体信息
     *
     * @param q_month_begin
     * @param q_month_end
     * @param q_projectId
     * @param q_projectName
     * @param q_billState
     * @param parseInt
     * @param parseInt2
     * @param user
     * @return
     */
    List<TMiguIncomeBilling> queryAllDrawBillIncome(String q_month_begin, String q_month_end, String q_projectId, String q_projectName,
                                                    String q_billState,String  q_month_bill,String q_incomeClassId,String q_incomeSectionId,String q_dept,
                                                    String q_userName,String q_bill_num,String q_bill_total, int parseInt, int parseInt2, MiguUsers user);

    /**
     * 查询项目信息-已经通过审核的需要开票的实际收入所在项目
     *
     * @param q_projectId
     * @param q_projectName
     * @param parseInt
     * @param parseInt2
     * @param user
     * @return
     */
    List<MiguIncomeManager> queryAllDrawBillIncome_1(String q_projectId, String q_projectName,
													int parseInt, int parseInt2, MiguUsers user);
    
    
    /**
     * 查询项目信息-已经通过审核的不需要开票的实际收入所在项目
     * @param q_projectId
     * @param q_projectName
     * @param parseInt
     * @param parseInt2
     * @param user
     * @return
     */
    List<MiguIncomeManager> queryAllNoBillIncome(String q_projectId, String q_projectName,
			int parseInt, int parseInt2, MiguUsers user);


	/**
	 * 查询预估收入明细
	 * @param incomeManagerId
	 * @return
	 */
	List<MiguIncomeDetail> viewIncomeDetails(String incomeManagerId);

	/**
	 * 新增预估收入
	 * @param request
	 * @param resonse
	 * @return
	 */
	Map<String, Object> addEstimateIncome(HttpServletRequest request, HttpServletResponse resonse);

	/**
	 * 修改预估收入
	 * @param request
	 * @param resonse
	 * @return
	 */
	Map<String, Object> saveEditEstimateIncome(HttpServletRequest request, HttpServletResponse resonse);
	/**
	 * 删除预估收入明细信息
	 * @param incomeDetailId
	 * @return
	 */
	Map<String, Object> delEstimateIncome(String incomeDetailId,String type);

	/**
	 * 刷新实际收入
	 * @param request
	 * @param resonse
	 * @return
	 */
	Map<String, Object> refreshIncome(HttpServletRequest request, HttpServletResponse resonse);
	
	Map<String, Object> viewRealIncomeDetails(String incomeManagerId);

	/**
	 * 查询待审核单据信息
	 * @param incomeManageId
	 * @param auditUser
	 * @return
	 */
	MiguIncomeManager selectExamineObj(String incomeManageId, String auditUser);


	 /**
	  * 审核预估收入
	  * @param map
	  * @return
	  */
	Map<String, Object> doExamine(Map<String, String> map,String title,String url,String type);

	/**
	 *查询审核历史
	 * @param incomeManagerId
	 * @return
	 */
	List<MiguIncomeManagerHis> queryList(String incomeManagerId,String type);

	void updateBillKey(String projectId, String incomeManagerId, String key) throws Exception;

    MiguIncomeManager queryByProjectIdAndIncomeManagerId(String projectId, String incomeManagerId);

	int insertBilling(TMiguIncomeBilling tMiguIncomeBilling);

	Map<String, Object> addRealIncome(HttpServletRequest request, HttpServletResponse resonse);
	
	public Map<String, Object> importProduct(HttpServletRequest request, HttpServletResponse response) throws Exception;

	public Map<String, Object> downLoadExcel(HttpServletRequest request, HttpServletResponse response,Map<String, Object>
	param)throws Exception;

	List<Map<String, Object>> selectEstimateIncome(String q_month, String q_projectId, String q_projectName,
			String q_estimateState, String realIncomeStatus,String q_incomeClassId,String q_incomeSectionId,
	        String q_dept ,String q_userName, String est_income,String est_exclusive_tax,String bzCycle, int parseInt, int parseInt2, MiguUsers user);

	int queryRecodes(String q_month, String q_projectId, String q_projectName, String q_estimateState,
			String realIncomeStatus, String q_incomeClassId,String q_incomeSectionId,
	        String q_dept ,String q_userName, String est_income,String est_exclusive_tax,String bzCycle,MiguUsers user);
	
	Map<String, Object> dellIncomeManager(String incomeManagerId);

	List<MiguIncomeManager> queryAllIncomeManager(String q_month, String q_projectId, String q_projectName,
			String q_estimateState,String q_incomeClassId,String q_incomeSectionId,
	        String q_dept ,String q_userName, String est_income,String est_exclusive_tax, String bzCycle,String incomeManagerIds, MiguUsers user);

	List<MiguIncomeManager> queryAllRealIncomeManager(String q_month, String q_projectId, String q_projectName,
			String realIncomeStatus, String incomeManagerIds,String q_incomeClassId, String q_incomeSectionId,
		        String q_dept, String q_userName, String est_income, String est_exclusive_tax,
		        String q_cx,  String q_bill,  String q_merge,String bzCycleReal,MiguUsers user);

	List<TMiguIncomeBilling> queryAllBill(String q_month_begin, String q_month_end, String q_projectId,
			String q_projectName, String q_billState,String billingKeys,String q_month_bill,String q_incomeClassId,String q_incomeSectionId,
	        String q_dept,String q_userName,String q_bill_num,String q_bill_total, MiguUsers user);

	String deleteBilling(String seq,String projectId) throws Exception;

	/**
	 * 查询合并实际收入条数
	 * @param q_month
	 * @param q_projectId
	 * @param q_projectName
	 * @return
	 */
	int queryTotal(String q_month, String q_projectId, String q_projectName,String bzCycleReal,String q_incomeClassId,String q_incomeSectionId,String q_dept,String q_userName,
			String rl_income,String rl_exclusive_tax,String q_cx);

	List<MiguIncomeManager> queryMergeIncome(String q_month, String q_projectId, String q_projectName,String bzCycleReal,String q_incomeClassId,String q_incomeSectionId,String q_dept,String q_userName,
			String rl_income,String rl_exclusive_tax,String q_cx, String page,String rows);

	Map<String, Object> queryMergeRealIncome(String dept_id,String startDate,String endDate, String page, String rows);

	Map<String, Object> searchProject(String projectid, String projectName,String userId, String page, String rows);

	Map<String, Object> mergeRealIncome(String ids, String projectId);

	Map<String, Object> viewMergeDetail(String mergeId);

	Map<String, Object> revokeMerge(List<MiguIncomeManager> list);

	List<MiguIncomeManager> viewMergeInfo(String id);

	List<MiguIncomeBill> viewBillings(String billingKey);
}
