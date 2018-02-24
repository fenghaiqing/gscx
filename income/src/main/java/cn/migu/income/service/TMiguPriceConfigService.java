package cn.migu.income.service;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.migu.income.pojo.TMiguPriceConfigInfo;
import cn.migu.income.util.PriceConfigPojo;

public interface TMiguPriceConfigService {

	/**
	 * 导入定价配置表
	 * @param in
	 * @param fileName
	 * @return
	 */
	Map<String, Object> importPriceCfg(HttpServletRequest request,HttpServletResponse response)throws Exception;
	
	/**
	 * 新增定价配置
	 * @param priceConfigPojo(aduitUser 审核人
	 *  projectId 项目id status 操作（保存、提交）)
	 * @return
	 */
	Map<String, Object> addMiGupriceConfig(PriceConfigPojo priceConfigPojo);

	/**
	 * 查询所有定价配置信息
	 * @param priceConfigId
	 * @param type
	 * @return
	 */
	Map<String, Object> queryAllPriceConfigInfo(String priceConfigId, String type);

	/**
	 * 删除定价配置详细信息
	 * @param priceConfigInfoId
	 * @return
	 */
	Map<String, Object> delPriceConfigInfo(List<String> list);

	Map<String, Object> selectPriceConfigForPage(String projectId, String page, String rows);

	int queryTotal(String projectId, String q_priceConfigNumber, String q_productId, String q_productName);

	List<TMiguPriceConfigInfo> selectMiguPriceConfigByProject(String projectId, String q_priceConfigNumber,
			String q_productId, String q_productName, int page, int pageSize);

	/**
	 *	查询入账管理产品信息
	 * @param projectId 项目编号
	 * @return
	 */
	List<TMiguPriceConfigInfo> selectProduct(String projectId,String cycle);

	List<TMiguPriceConfigInfo> queryAllpriceConfig(String priceConfigWin_projectId, String q_productId,
			String q_productName);

	
}
