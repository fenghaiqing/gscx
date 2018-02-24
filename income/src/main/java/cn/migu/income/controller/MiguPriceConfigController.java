package cn.migu.income.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import cn.migu.income.pojo.MiguUsers;
import cn.migu.income.pojo.TMiguPriceConfigInfo;
import cn.migu.income.service.TMiguPriceConfigService;
import cn.migu.income.util.MiguConstants;
import cn.migu.income.util.PriceConfigPojo;
import cn.migu.income.util.PropValue;
import cn.migu.income.util.StringUtil;
import cn.migu.income.util.TxtFileOperation;

@Controller
@RequestMapping(value = "/priceConfig")
public class MiguPriceConfigController {

	final static Logger log = LoggerFactory.getLogger(MiguPriceConfigController.class);

	@Autowired
	private TMiguPriceConfigService priceConfigService;

	/**
	 * 定价配置管理页面
	 * 
	 * @author chentao
	 */
	@RequestMapping(value = "/priceConfigManage")
	public ModelAndView priceConfigManage(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("priceConfigManage");
		return mav;
	}
	
	
	/**
	 * 查询定价配置
	 * @param request
	 * @param session
	 * @param projectId
	 * @param q_priceConfigNumber
	 * @param q_productId
	 * @param q_productName
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/selectMiguPriceConfigByProject", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> selectMiguPriceConfigByProject(HttpServletRequest request,HttpSession session, 
	        String projectId,String q_priceConfigNumber,String q_productId,String q_productName,String page,String rows)
	    {
	        Map<String, Object> map = new HashMap<String, Object>();
	        int count=0;
	        List<TMiguPriceConfigInfo> list =null;
	        try
	        {
	            if (StringUtils.isEmpty(page))
	            {
	                page = "1";
	            }
	            if (StringUtils.isEmpty(rows))
	            {
	                rows = "10";
	            }            
	            if (StringUtils.isEmpty(q_priceConfigNumber))
	            {
	            	q_priceConfigNumber = null;
	            }
	            if (StringUtils.isEmpty(q_productId))
	            {
	            	q_productId = null;
	            }
	            if (StringUtils.isEmpty(q_productName))
	            {
	            	q_productName = null;
	            }
	            if(StringUtil.isNotEmpty(q_priceConfigNumber)&&(q_priceConfigNumber.contains("_")||q_priceConfigNumber.contains("%")))
	            {
	            	q_priceConfigNumber=StringUtil.getSpecialParam(q_priceConfigNumber);
	            }
	            if(StringUtil.isNotEmpty(q_productId)&&(q_productId.contains("_")||q_productId.contains("%")))
	            {
	            	q_productId=StringUtil.getSpecialParam(q_productId);
	            }
	            if(StringUtil.isNotEmpty(q_productName)&&(q_productName.contains("_")||q_productName.contains("%")))
	            {
	            	q_productName=StringUtil.getSpecialParam(q_productName);
	            }
	            count = priceConfigService.queryTotal(projectId, q_priceConfigNumber, q_productId,q_productName);
	            list = priceConfigService.selectMiguPriceConfigByProject(projectId, q_priceConfigNumber, q_productId,
	            		q_productName,Integer.parseInt(page), Integer.parseInt(rows));
	        }
	        catch (Exception e)
	        {
	            log.info("项目查询异常:", e);
	            e.printStackTrace();
	        }
	        map.put("total", count);
	        map.put("rows", list);
	        return map;
	    }
	
	
	
	

	/**
	 * 根据项目查询定价配置
	 * 
	 * @param projectId
	 * @return
	 */
	@RequestMapping(value = "/selectPriceConfigForPage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectPriceConfigForPage(HttpServletRequest request, HttpSession session,
			String projectId,String page,String rows) {

		return priceConfigService.selectPriceConfigForPage(projectId,page,rows);
	}
	/**
	 * 导入定价配置
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/priceCfgImport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return priceConfigService.importPriceCfg(request, response);
	}

	/**
	 * 下载模版文件
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value = "/downLoad", method = RequestMethod.POST)
	public @ResponseBody String downLoad(HttpServletRequest request, HttpSession session, HttpServletResponse response)
			throws FileNotFoundException {
		String rootpath = request.getSession().getServletContext().getRealPath("/");
		File file = new File(rootpath + "/export/定价配置.xlsx");
		if (file.exists()) {
			TxtFileOperation.downloadLocal(response, file);
		} else {
			log.error("项目附件下载功能异常，文件不存在，请检查！");
			try {
				return new String("项目附件下载功能异常，文件不存在，请检查！".getBytes(), "ISO-8859-1");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 查询所有定价配置信息
	 * 
	 * @param request
	 * @param session
	 * @param projectId
	 * @param priceConfigId
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/queryAllPriceConfigInfo", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> queryAllPriceConfigInfo(HttpServletRequest request, HttpSession session,
			 String projectId, String type) {
		return priceConfigService.queryAllPriceConfigInfo(projectId,type);
	}

	/**
	 * 手动添加定价配置详细信息
	 * 
	 * @param priceConfigInfos
	 * @param projectId
	 * @return
	 */
	@RequestMapping(value = "/addMiGupriceConfig", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> addPriceConfig(@RequestBody PriceConfigPojo priceConfigPojo) {
		return priceConfigService.addMiGupriceConfig(priceConfigPojo);
	}

	@RequestMapping(value = "/delPriceConfigInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delPriceConfigInfo(@RequestBody List<String> list){
		return priceConfigService.delPriceConfigInfo(list);
	}
	
	/**
	 * 查询入账管理 产品信息
	 * @param projectId
	 * @return
	 */
	@RequestMapping(value = "/selectProduct", method = RequestMethod.POST)
	@ResponseBody
	public List<TMiguPriceConfigInfo> selectProduct(String projectId,String cycle){
		return priceConfigService.selectProduct(projectId,cycle);
	}
	
	/**
     * 定价配置导出
     * @param request
     * @param session
     * @param provCode
     * @param chCode
     * @param activeDate
     * @return
     * @throws UnsupportedEncodingException 
     */
    @RequestMapping(value = "/exportFile", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> exportFile(HttpServletRequest request, HttpSession session,
        String priceConfigWin_projectId, String q_productId, String q_productName) throws UnsupportedEncodingException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        
        if (StringUtils.isEmpty(priceConfigWin_projectId))
        {
        	priceConfigWin_projectId = null;
        }
        if(StringUtils.isEmpty(q_productId))
        {
        	q_productId = null;
        }
        if(StringUtils.isEmpty(q_productName))
        {
        	q_productName = null;
        }
        
        List<TMiguPriceConfigInfo> dataList = new ArrayList<TMiguPriceConfigInfo>();
		try {
			dataList = priceConfigService.queryAllpriceConfig(priceConfigWin_projectId, q_productId, q_productName);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        
        if (dataList.size() == 0)
        {
            log.info("该条件下无定价配置数据");
            map.put("reCode", -1);
            map.put("reStr", "该条件下无定价配置数据");
        }
        else
        {
        	
        	WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
            ServletContext servletContext = webApplicationContext.getServletContext();          
        	String tempFile= servletContext.getRealPath("/reportTemplate")+"/定价配置管理列表.xls";//模板文件的地址
        	File temp_file = new File(tempFile);
        	
        	
        	String tempFolder = PropValue.getPropValue("report_income_diff");
            File folderTemp = new File(tempFolder);
            if(!folderTemp.isDirectory()&&!folderTemp.exists()){
                folderTemp.mkdirs();
            }
            File reportFile = new File(tempFolder + temp_file.getName());//创建传送文件
            if (reportFile.exists())
            {
            	reportFile.delete();
            }
            try
            {
            	reportFile.createNewFile();
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return null;
            }
            try {
				FileUtils.copyFile(temp_file, reportFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
            
            
            HSSFWorkbook hssfworkbook = null;
			try {
				hssfworkbook = new HSSFWorkbook(new FileInputStream(reportFile));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
            HSSFSheet    hssfsheet    = hssfworkbook.getSheetAt(0);
            int i = hssfsheet.getPhysicalNumberOfRows();
            
            HSSFCellStyle style1 = hssfworkbook.createCellStyle();
            style1.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style1.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style1.setTopBorderColor(HSSFColor.BLACK.index);
            style1.setLeftBorderColor(HSSFColor.BLACK.index);
            style1.setRightBorderColor(HSSFColor.BLACK.index);
            style1.setBottomBorderColor(HSSFColor.BLACK.index);
            
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");//格式化设置  
            
            for (int j = 0; j < dataList.size(); j++) {
            	TMiguPriceConfigInfo tMiguPriceConfigInfo = (TMiguPriceConfigInfo) dataList.get(j);
            	//创建一行  
            	HSSFRow rowData = hssfsheet.createRow(i++);  
                createCell(style1, rowData, 0, StringUtil.null2Blank(tMiguPriceConfigInfo.getProductId()));  
                createCell(style1, rowData, 1, StringUtil.null2Blank(new String(tMiguPriceConfigInfo.getProductName().getBytes("UTF-8"),"UTF-8"))); 
                
                if(tMiguPriceConfigInfo.getVendorId()==null){
                	createCell(style1, rowData, 2, "");
                }else{
                	createCell(style1, rowData, 2, StringUtil.null2Blank(tMiguPriceConfigInfo.getVendorId()));
                }
                
                if(tMiguPriceConfigInfo.getVendorName()==null){
                	createCell(style1, rowData, 3, "");
                }else{
                	createCell(style1, rowData, 3, StringUtil.null2Blank(new String(tMiguPriceConfigInfo.getVendorName().getBytes("UTF-8"),"UTF-8")));
                }
                createCell(style1, rowData, 4, StringUtil.null2Blank(tMiguPriceConfigInfo.getPurchasePrice()==null?"":decimalFormat.format(tMiguPriceConfigInfo.getPurchasePrice())));  
                createCell(style1, rowData, 5, StringUtil.null2Blank(tMiguPriceConfigInfo.getMinSellPrice()==null?"":decimalFormat.format(tMiguPriceConfigInfo.getMinSellPrice()))); 
                createCell(style1, rowData, 6, StringUtil.null2Blank(tMiguPriceConfigInfo.getOfferStartTime()));
                createCell(style1, rowData, 7, StringUtil.null2Blank(tMiguPriceConfigInfo.getOfferEndTime()));
                if(tMiguPriceConfigInfo.getIllustrate()==null){
                	createCell(style1, rowData, 8, "");
                }else{
                	createCell(style1, rowData, 8, StringUtil.null2Blank(new String(tMiguPriceConfigInfo.getIllustrate().getBytes("UTF-8"),"UTF-8")));
                }
			}
            
            OutputStream os = null;
			try {
				os = new FileOutputStream(reportFile);
				hssfworkbook.write(os);
				os.close();
				map.put("reCode", 100);
				map.put("fileName", reportFile.getName());
				log.info("定价配置文件生成成功");
			} catch (Exception e) {
				map.put("reCode", -1);
				map.put("reStr", "定价配置文件生成异常");
				e.printStackTrace();
			}
        }
        return map;
    }
    
  //poi表格行创建方法
    public static void createCell(HSSFCellStyle style, HSSFRow row, int column,  
            String value) {  
    	@SuppressWarnings("deprecation")
		HSSFCell cell = row.createCell((short)column);
        cell.setCellStyle(style);
        cell.setCellValue(value);
    }
    
    /**
     * 下载已上传附件
     * @param request
     * @param session
     * @param response
     * @param fileName
     * @throws FileNotFoundException
     */
    @RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
    public @ResponseBody int downloadFile(HttpServletRequest request, HttpSession session, HttpServletResponse response,String fileName)
        throws FileNotFoundException
    {        
        String filePath = PropValue.getPropValue("report_income_diff"); //文件路径
        File downloadFile = new File(filePath + fileName);
        try{
	        if (downloadFile.exists())
	        {
	            TxtFileOperation.downloadLocal(response, downloadFile);
	        }
	        return 1;//成功
        }catch(Exception e){
        	log.error("下载已上传附件异常:", e);
        	return 0;//失败
        }
    }
}
