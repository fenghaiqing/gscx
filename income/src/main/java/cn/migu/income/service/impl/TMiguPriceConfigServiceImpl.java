package cn.migu.income.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.migu.income.dao.MiguIncomeDetailMapper;
import cn.migu.income.dao.TMiguPriceConfigInfoMapper;
import cn.migu.income.dao.TMiguPriceConfigMapper;
import cn.migu.income.pojo.MiguIncomeDetail;
import cn.migu.income.pojo.TMiguPriceConfig;
import cn.migu.income.pojo.TMiguPriceConfigInfo;
import cn.migu.income.pojo.TMiguProjectBase;
import cn.migu.income.service.TMiguPriceConfigService;
import cn.migu.income.util.ImportExcelUtil;
import cn.migu.income.util.PriceConfigPojo;
import cn.migu.income.util.RetCode;
import cn.migu.income.util.StringUtil;

@Service
public class TMiguPriceConfigServiceImpl implements TMiguPriceConfigService {

	@Autowired
	private TMiguPriceConfigMapper priceConfigMapper;
	@Autowired
	private TMiguPriceConfigInfoMapper priceConfigInfoMapper;
	@Autowired
	private MiguIncomeDetailMapper incomeDetail;

	private final static String ADUITRESULT = "1";

	private final static String OPRATIONTYPE = "add";
	final static Logger log = LoggerFactory.getLogger(TMiguPriceConfigServiceImpl.class);

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public Map<String, Object> importPriceCfg(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		InputStream in = null;
		Map<String, String> map = new HashMap<String, String>();
		Workbook work = null;
		Sheet sheet = null;
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<?> items = upload.parseRequest(request);
		Iterator<?> itr = items.iterator();
		while (itr.hasNext()) {
			FileItem item = (FileItem) itr.next();
			if (!item.isFormField()) {
				in = item.getInputStream();
				try {
					work = ImportExcelUtil.getWorkbook(in, item.getName());
				} catch (Exception e) {
					log.error("操作时间：【"+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date())+"】,操作内容：【定价配置导入】，异常信息：【"+e.getMessage()+"】");
					return RetCode.serverError("文件格式有误，请导入模板文件！");
				}
			} else {
				map.put(item.getFieldName(), item.getString("utf-8"));
			}
		}
		if (null == work) {
			return RetCode.serverError("创建Excel工作薄为空！");
		}

		// 遍历Excel中所有的sheet
		// for (int i = 0; i < work.getNumberOfSheets(); i++) {
		sheet = work.getSheetAt(0);
		if (sheet == null) {
			return RetCode.serverError("Excel工作薄为空！");
		}
		List<TMiguPriceConfigInfo> list = null;
		try {
			// 解析excel返回定价配置信息集合
			list = getPriceConfigInfoList(sheet);
			//全量导入
		/*	if("all".equals(map.get("importType"))){
				int rows =priceConfigInfoMapper.deleteByProjectId( map.get("priceConfig_projectid"));
				log.info("全量导入定价配置信息：共清除"+ map.get("priceConfig_projectid")+"项目下"+rows+"条历史定价配置信息");
			}*/
			// 保存定价配置明细信息
			this.excute(list, map.get("priceConfig_projectid"));
			return RetCode.success("导入成功！");
		} catch (Exception e) {
			e.printStackTrace();
			// 事物回滚
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return RetCode.serverError(e.getMessage());
		}

	}

	private String getCellVale(Cell cell) {
		return ImportExcelUtil.getCellValue(cell).toString();
	}

	private List<TMiguPriceConfigInfo> getPriceConfigInfoList(Sheet sheet) {
		Row row = null;
		Cell cell = null;
		TMiguPriceConfigInfo priceConfigInfo = null;
		List<TMiguPriceConfigInfo> list = new ArrayList<>();
		// 遍历当前sheet中的所有行
		boolean flag = false;
		int line = 0;
		if (sheet.getLastRowNum() < 1) {
			throw new RuntimeException("excel校验失败，数据无法导入，请修正后重新选择附件提交！");
		}
		for (int j = 1; j <= sheet.getLastRowNum(); j++) {
			line = j + 1;
			row = sheet.getRow(j);
			if (row == null) {
				flag = true;
				break;
			}
			// 遍历所有的列
			priceConfigInfo = new TMiguPriceConfigInfo();

			if (row.getCell(0) != null && StringUtil.isNotEmpty(getCellVale(row.getCell(0)))) {
				priceConfigInfo.setProductId(getCellVale(row.getCell(0)).trim());
			} else {
				flag = true;
				break;
			}
			if (row.getCell(1) != null && StringUtil.isNotEmpty(getCellVale(row.getCell(1)))) {
				priceConfigInfo.setProductName(getCellVale(row.getCell(1)).trim());
			} else {
				flag = true;
				break;
			}

			if (row.getCell(2) != null && StringUtil.isNotEmpty(getCellVale(row.getCell(2)))) {
				priceConfigInfo.setVendorId(getCellVale(row.getCell(2)).trim());
			}

			if (row.getCell(3) != null && StringUtil.isNotEmpty(getCellVale(row.getCell(3)))) {
				priceConfigInfo.setVendorName(getCellVale(row.getCell(3)).trim());
			}

			if (row.getCell(4) != null) {
				cell = row.getCell(4);
				String purchasePrice = getCellVale(cell);
				if (StringUtil.isEmpty(purchasePrice) || !StringUtil.isNumeric(purchasePrice)) {
					flag = true;
					break;
				} else {
					priceConfigInfo.setPurchasePrice(BigDecimal.valueOf(Double.parseDouble(purchasePrice.trim())));
				}
			} else {
				flag = true;
				break;
			}
			if (row.getCell(5) != null) {
				cell = row.getCell(5);
				String minPrice = getCellVale(cell);
				if (StringUtil.isEmpty(minPrice) || !StringUtil.isNumeric(minPrice)) {
					flag = true;
					break;
				} else {
					priceConfigInfo.setMinSellPrice(BigDecimal.valueOf(Double.parseDouble(minPrice.trim())));
				}
			} else {
				flag = true;
				break;
			}
			if (row.getCell(6) != null) {
				cell = row.getCell(6);
				if (StringUtil.isNotEmpty(getCellVale(cell))
						&& StringUtil.checkStringFormatDate(getCellVale(cell), "yyyyMM")) {
					priceConfigInfo.setOfferStartTime(getCellVale(cell));
				} else {
					flag = true;
					break;
				}
			} else {
				flag = true;
				break;
			}
			if (row.getCell(7) != null) {
				cell = row.getCell(7);
				if (StringUtil.isNotEmpty(getCellVale(cell))
						&& StringUtil.checkStringFormatDate(getCellVale(cell), "yyyyMM")) {
					priceConfigInfo.setOfferEndTime(getCellVale(cell));
				} else {
					flag = true;
					break;
				}
			} else {
				flag = true;
				break;
			}
			// 说明
			if (row.getCell(8) != null) {
				cell = row.getCell(8);
				if (StringUtil.isNotEmpty(getCellVale(cell))) {
					priceConfigInfo.setIllustrate(getCellVale(cell).trim());
				}
			}

			// priceConfigInfo.setIllustrate(explain);
			list.add(priceConfigInfo);
		}
		if (flag) {
			throw new RuntimeException("第" + line + "行校验失败，数据无法导入，请修正后重新选择附件提交！");
		}
		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public Map<String, Object> queryAllPriceConfigInfo(String projectId, String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<TMiguPriceConfigInfo> list = new ArrayList<>();
		try {
			if (!"add".equals(type)) {
				list = priceConfigInfoMapper.selectByprojectId(projectId);
			}
		} catch (Exception e) {
			log.info("项目查询异常:", e);
			return RetCode.serverError();
		}
		map.put("rows", list);
		return map;

	}

	@Override
	public Map<String, Object> delPriceConfigInfo(List<String> list) {
		try {
			for (String string : list) {
				// 是否已被使用
				List<MiguIncomeDetail> detaillist = incomeDetail.selectByPriceConfigId(string);
				if (detaillist != null && detaillist.size() > 0) {
					return RetCode.success("msg", "该产品定价信息已被使用，无法删除！");
				}
				priceConfigInfoMapper.deleteByPrimaryKey(Integer.parseInt(string));
			}
			return RetCode.success();

		} catch (Exception e) {
			log.info("删除定价配置异常:", e);
			return RetCode.serverError();
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public Map<String, Object> addMiGupriceConfig(PriceConfigPojo priceConfigPojo) {

		try {
			if (!OPRATIONTYPE.equals(priceConfigPojo.getOpration())) {// 更新操作

				return this.excute(priceConfigPojo.getList(), priceConfigPojo.getProjectId());

			} else {// 新增操作

				// 保存定价配置明细信息
				return this.excute(priceConfigPojo.getList(), priceConfigPojo.getProjectId());
			}

		} catch (Exception e) {
			log.error("新增/修改定价配置异常" + e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return RetCode.serverError(e.getMessage());
		}
	}

	private Map<String, Object> excute(List<TMiguPriceConfigInfo> priceCofigInfoList, String projectId)
			throws Exception {

		Map<String, Object> map = null;
		StringBuffer sb = new StringBuffer("");
		// 保存定价配置明细信息
		for (TMiguPriceConfigInfo miguPriceConfigInfo : priceCofigInfoList) {

			map = new HashMap<>();

			String startTime = miguPriceConfigInfo.getOfferStartTime();
			String endTime = miguPriceConfigInfo.getOfferEndTime();

			// 判断报价开始日期是否大于报价结束日期
			if (!StringUtil.compare_date(endTime, startTime, "yyyyMM")) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				throw new Exception(RetCode.PRICE_CONFIG_ERROR_A);
			}
			miguPriceConfigInfo.setOfferEndTime(StringUtil.formatDateToString(endTime));
			miguPriceConfigInfo.setOfferStartTime(StringUtil.formatDateToString(startTime));

			// 判断长度
			if (StringUtil.getStringBytesLength(miguPriceConfigInfo.getProductId().trim()) > 20) {
				throw new Exception(RetCode.PRICE_CONFIG_ERROR_C);
			}
			if (StringUtil.getStringBytesLength(miguPriceConfigInfo.getProductName().trim()) > 30) {
				throw new Exception(RetCode.PRICE_CONFIG_ERROR_D);
			}
			if (miguPriceConfigInfo.getVendorId()!=null&&!"".equals(miguPriceConfigInfo.getVendorId().trim())
					&& StringUtil.getStringBytesLength(miguPriceConfigInfo.getVendorId()) > 20) {
				throw new Exception(RetCode.PRICE_CONFIG_ERROR_G);
			}
			if (miguPriceConfigInfo.getVendorName()!=null&&!"".equals(miguPriceConfigInfo.getVendorName().trim())
					&& StringUtil.getStringBytesLength(miguPriceConfigInfo.getVendorName()) > 200) {
				throw new Exception(RetCode.PRICE_CONFIG_ERROR_H);
			}
			String purchasePrice = String.valueOf(miguPriceConfigInfo.getPurchasePrice().doubleValue());
			if (StringUtil.getStringBytesLength(purchasePrice) > 10) {
				throw new Exception(RetCode.PRICE_CONFIG_ERROR_F);
			}
			String minSellPrice = String.valueOf(miguPriceConfigInfo.getMinSellPrice().doubleValue());
			if (StringUtil.getStringBytesLength(minSellPrice) > 10) {
				throw new Exception(RetCode.PRICE_CONFIG_ERROR_F);
			}
			if (StringUtil.isNotEmpty(miguPriceConfigInfo.getIllustrate())
					&& StringUtil.getStringBytesLength(miguPriceConfigInfo.getIllustrate()) > 200) {
				throw new Exception(RetCode.PRICE_CONFIG_ERROR_E);
			}

			map.put("productId", miguPriceConfigInfo.getProductId());
			map.put("offerStartTime", startTime);
			map.put("offerEndTime", endTime);
			map.put("projectId", projectId);

			List<TMiguPriceConfigInfo> list = priceConfigInfoMapper.selectByExample(map);
			// 判断是否是新增
			if (miguPriceConfigInfo.getPriceConfigInfoId() == null) {
				// 检查 同一项目 同一报价周期 是否存在相同产品
				if (list != null && list.size() > 0) {
					throw new Exception(RetCode.PRICE_CONFIG_ERROR_B);
				}
				miguPriceConfigInfo.setProjectId(projectId);
				priceConfigInfoMapper.insertSelective(miguPriceConfigInfo);
			} else {
				if ((list != null && list.size() == 1
						&& list.get(0).getPriceConfigInfoId().equals(miguPriceConfigInfo.getPriceConfigInfoId()))
						|| list.size() < 1) {
					// 是否已被使用
					List<MiguIncomeDetail> detaillist = incomeDetail
							.selectByPriceConfigId(String.valueOf(miguPriceConfigInfo.getPriceConfigInfoId()));
					if (detaillist != null && detaillist.size() > 0) {
						sb.append(miguPriceConfigInfo.getProductId() + ",");
						continue;
					}
					priceConfigInfoMapper.updateByPrimaryKey(miguPriceConfigInfo);
				} else {
					throw new Exception(RetCode.PRICE_CONFIG_ERROR_B);
				}

			}
		}
		String msg = sb.toString();
		return RetCode.success("msg", StringUtil.isEmpty(msg) ? null : msg.substring(0, msg.lastIndexOf(",")));
	}

	@Override
	public Map<String, Object> selectPriceConfigForPage(String projectId, String page, String rows) {
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		if (StringUtil.isEmpty(page)) {
			page = "1";
		}
		if (StringUtil.isEmpty(rows)) {
			rows = "10";
		}
		map.put("projectId", projectId);
		map.put("page", page);
		map.put("pageSize", rows);
		List<TMiguPriceConfig> list = priceConfigMapper.selectPriceConfigForPage(map);
		Integer total = priceConfigMapper.selectCount(projectId);
		result.put("rows", list);
		result.put("total", total);
		return result;
	}

	@Override
	public int queryTotal(String projectId, String q_priceConfigNumber, String q_productId, String q_productName) {
		Map<String, Object> param = new HashMap<String, Object>();
		if (!"".equals(projectId) && projectId != null) {
			param.put("projectId", projectId);
		}
		if (!"".equals(q_priceConfigNumber) && q_priceConfigNumber != null) {
			param.put("q_priceConfigNumber", q_priceConfigNumber);
		}
		if (!"".equals(q_productId) && q_productId != null) {
			param.put("q_productId", q_productId);
		}
		if (!"".equals(q_productName) && q_productName != null) {
			param.put("q_productName", q_productName);
		}
		return priceConfigInfoMapper.queryTotal(param);
	}

	@Override
	public List<TMiguPriceConfigInfo> selectMiguPriceConfigByProject(String projectId, String q_priceConfigNumber,
			String q_productId, String q_productName, int page, int pageSize) {
		Map<String, Object> param = new HashMap<String, Object>();
		if (!"".equals(projectId) && projectId != null) {
			param.put("projectId", projectId);
		}
		if (!"".equals(q_priceConfigNumber) && q_priceConfigNumber != null) {
			param.put("q_priceConfigNumber", q_priceConfigNumber);
		}
		if (!"".equals(q_productId) && q_productId != null) {
			param.put("q_productId", q_productId);
		}
		if (!"".equals(q_productName) && q_productName != null) {
			param.put("q_productName", q_productName);
		}
		param.put("curPage", page);
		param.put("pageSize", pageSize);
		List<TMiguPriceConfigInfo> list = priceConfigInfoMapper.selectMiguPriceConfigByProject(param);
		return list;
	}

	@Override
	public List<TMiguPriceConfigInfo> selectProduct(String projectId, String cycle) {
		Map<String, Object> map = new HashMap<>();
		map.put("projectId", projectId);
		map.put("today", StringUtil.formatDateToyyyyMM(cycle));
		return priceConfigInfoMapper.selectProduct(map);
	}
	
	@Override
	public List<TMiguPriceConfigInfo> queryAllpriceConfig(String projectId,
			String q_productId, String q_productName) {
		Map<String, Object> param = new HashMap<String, Object>();
		if (!"".equals(projectId) && projectId != null) {
			param.put("projectId", projectId);
		}
		if (!"".equals(q_productId) && q_productId != null) {
			param.put("q_productId", q_productId);
		}
		if (!"".equals(q_productName) && q_productName != null) {
			param.put("q_productName", q_productName);
		}
		List<TMiguPriceConfigInfo> list = priceConfigInfoMapper.queryAllpriceConfig(param);
		return list;
	}

}
