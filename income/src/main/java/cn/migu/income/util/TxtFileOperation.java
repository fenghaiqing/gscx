package cn.migu.income.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TxtFileOperation {
    final static Logger log = LoggerFactory.getLogger(TxtFileOperation.class);

    /**
     * chentao <获取接口尾文件> <功能详细描述>
     * 
     * @param string
     * @return
     * @throws FileNotFoundException
     * @see [类、类#方法、类#成员]
     */
    public static String getTrailerFile(String fileNameKey) throws FileNotFoundException {
	String trailerFilePath = "";
	File file = new File(PropValue.getPropValue(MiguConstants.TEP_LOCATION));
	if (file.isDirectory()) {
	    String[] filelist = file.list();
	    for (int i = 0; i < filelist.length; i++) {
		File readfile = new File(PropValue.getPropValue(MiguConstants.TEP_LOCATION) + filelist[i]);
		if (!readfile.isDirectory()) {
		    if (readfile.getPath().indexOf(fileNameKey) > 0) {
			if (readfile.getPath().indexOf("_999") > 0) {
			    trailerFilePath = readfile.getPath();
			}
		    }
		}
	    }
	}
	if (trailerFilePath.equals("")) {
	    throw new FileNotFoundException("不存在" + fileNameKey + "接口的尾文件");
	}
	return trailerFilePath;
    }

    /**
     * chentao <获取接口源文件> <功能详细描述>
     * 
     * @param string
     * @return
     * @throws FileNotFoundException
     * @see [类、类#方法、类#成员]
     */
    public static List<String> getSourceFileList(String fileNameKey) throws FileNotFoundException {
	List<String> sourceFileList = new ArrayList<>();
	File file = new File(PropValue.getPropValue(MiguConstants.TEP_LOCATION));
	if (file.isDirectory()) {
	    String[] filelist = file.list();
	    for (int i = 0; i < filelist.length; i++) {
		File readfile = new File(PropValue.getPropValue(MiguConstants.TEP_LOCATION) + filelist[i]);
		if (!readfile.isDirectory()) {
		    if (readfile.getPath().indexOf(fileNameKey) >= 0) {
			if (readfile.getPath().indexOf("_999") < 0) {
			    sourceFileList.add(readfile.getPath());
			}
		    }
		}
	    }
	}
	if (sourceFileList.size() == 0) {
	    throw new FileNotFoundException("不存在" + fileNameKey + "接口的源文件");
	}
	return sourceFileList;
    }

    /**
     * chentao <获取尾文件所有数据返回Map> <功能详细描述>
     * 
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static Map<String, Integer> readTrailerFile(String trailerFilePath) {
	Map<String, Integer> trailerFileMap = new HashMap<String, Integer>();
	String trailerFileRowData = "";// 尾文件行数据
	Scanner trailerFile = null;
	try {
	    trailerFile = new Scanner(new File(trailerFilePath));
	} catch (FileNotFoundException e) {
	    log.info("接口尾文件不存在:" + trailerFilePath);
	    e.printStackTrace();
	}
	if (trailerFile != null) {
	    while (trailerFile.hasNextLine()) {
		trailerFileRowData = trailerFile.nextLine();
		String[] data = trailerFileRowData.split(",");
		String trailerFileName = data[0];
		int trailerFileCount = Integer.parseInt(data[1]);
		trailerFileMap.put(trailerFileName, trailerFileCount);
	    }
	    trailerFile.close();
	}
	return trailerFileMap;
    }

    /**
     * chentao <读取txt文件> <功能详细描述>
     * 
     * @param fileInputStream
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static List<String> readTxtFile(String loc) {
	List<String> txtFileDatas = new ArrayList<String>();
	InputStreamReader read = null;
	BufferedReader reader = null;
	try {
	    String code = StringUtil.getCharset(loc);
	    if (code.equals("GBK")) {
		read = new InputStreamReader(new FileInputStream(new File(loc)), "GBK");
	    } else if (code.equals("UTF-8")) {
		read = new InputStreamReader(new FileInputStream(new File(loc)), "UTF-8");
	    }
	    reader = new BufferedReader(read);
	    for (String line = reader.readLine(); line != null; line = reader.readLine()) {
		line = line.trim();
		txtFileDatas.add(line);
	    }
	} catch (UnsupportedEncodingException e) {
	    log.info("文件编码错误", e);
	    e.printStackTrace();
	} catch (IOException e) {
	    log.info("文件流异常", e);
	    e.printStackTrace();
	} finally {
	    try {
		read.close();
		reader.close();
	    } catch (IOException e) {
		log.info("文件流关闭失败", e);
		e.printStackTrace();
	    }
	}
	return txtFileDatas;
    }

    /**
     * chentao <检查文件名是否符合规范> <功能详细描述>
     * 
     * @param fileNameKey
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean checkFileName(String fileName, String fileNameKey) {
	String fileExtensions = fileName.substring(fileName.indexOf(".") + 1, fileName.length());// 文件后缀名
	fileName = fileName.substring(0, fileName.indexOf("."));// 文件名称
	if (!fileExtensions.toLowerCase().equals("txt")) {
	    log.info("导入的文件名称不符合规范，请查验");
	    return false;
	}
	if (fileName.indexOf(fileNameKey) < 0) {
	    log.info("导入的文件名称不符合规范，请查验");
	    return false;
	}
	if (fileNameKey.equals("CH_") || fileNameKey.equals("BI_") || fileNameKey.equals("SETT_")) {
	    if (fileName.split("_").length != 3) {
		log.info("导入的文件名称不符合规范，请查验");
		return false;
	    }
	} else {
	    if (fileName.split("_").length != 2) {
		log.info("导入的文件名称不符合规范，请查验");
		return false;
	    }
	}

	String name = fileName.split("_")[1];
	for (int i = 0; i < name.length(); i++) { // 循环遍历字符串
	    if (Character.isLetter(name.charAt(i))) { // 用char包装类中的判断字母的方法判断每一个字符
		log.info("导入的文件名称不符合规范，请查验");
		return false;
	    }
	}

	if (fileNameKey.equals("CH_") || fileNameKey.equals("REMU_")) {
	    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	    format.setLenient(false);
	    if (fileName.split("_")[1].length() != 8) {
		log.info("导入的文件名称不符合规范，请查验");
		return false;
	    }
	    try {
		format.parse(fileName.split("_")[1]);
	    } catch (ParseException e) {
		log.info("导入的文件名称不符合规范，请查验");
		return false;
	    }
	} else {
	    SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
	    format.setLenient(false);
	    try {
		format.parse(fileName.split("_")[1]);
	    } catch (ParseException e) {
		log.info("导入的文件名称不符合规范，请查验");
		return false;
	    }
	}

	return true;
    }

    public static boolean copyFile(File srcFile, File destFile, boolean ifDelete) {
	try {
	    FileUtils.copyFile(srcFile, destFile);
	    if (ifDelete) {
		srcFile.delete();
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	    return false;
	}
	return true;
    }

    public static void downloadLocal(HttpServletResponse response, File file) throws FileNotFoundException {
	BufferedInputStream bis = null;
	BufferedOutputStream bos = null;
	try {

	    String fileName = file.getName();
	    response.setContentType("application/octet-stream");
	    response.setCharacterEncoding("UTF-8");
	    // response.setHeader("Content-disposition", "attachment; filename="
	    // + fileName);//new String(as.getBytes("utf-8"), "ISO_8859_1")
	    response.setHeader("Content-disposition",
		    "attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO-8859-1"));
	    response.setContentLength((int) file.length());
	    bis = new BufferedInputStream(new FileInputStream(file));
	    bos = new BufferedOutputStream(response.getOutputStream());
	    byte[] buff = new byte[2048];
	    int bytesRead = 0;
	    while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
		bos.write(buff, 0, bytesRead);
	    }
	    bos.flush();
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    if (bis != null) {
		try {
		    bis.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		bis = null;
	    }
	    if (bos != null) {
		try {
		    bos.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		bos = null;
	    }
	}
    }

    public static void moveErrorFile(String timeStr, Map<String, String> sourceFiles_ch, String errorInfo) {
	Iterator<String> keySetIterator = sourceFiles_ch.keySet().iterator();
	while (keySetIterator.hasNext()) {
	    File file = new File(sourceFiles_ch.get(keySetIterator.next()));
	    File destFile = new File(PropValue.getPropValue(MiguConstants.ERROR_LOCATION)
		    + file.getName().substring(0, file.getName().length() - ".txt".length()) + "_error" + "." + timeStr
		    + ".txt");
	    if (TxtFileOperation.copyFile(file, destFile, true)) {
		log.info("【渠道自动导入】 警告：" + errorInfo + "，源文件" + file.getName() + "已移入错误文件夹中！");
	    } else {
		log.info("【渠道自动导入】 警告：" + errorInfo + "，源文件" + file.getName() + "移入错误文件夹出错，请确认！");
	    }
	}
    }

    public static void moveErrorFile(String timeStr, File channelFile, String errorInfo) {
	File destFile = new File(PropValue.getPropValue(MiguConstants.ERROR_LOCATION)
		+ channelFile.getName().substring(0, channelFile.getName().length() - ".txt".length()) + "_error" + "."
		+ timeStr + ".txt");
	if (TxtFileOperation.copyFile(channelFile, destFile, true)) {
	    log.info("【渠道自动导入】 警告：文件" + channelFile.getName() + errorInfo + "!，文件已成功移至错误文件夹！");
	} else {
	    log.info("【渠道自动导入】 警告：文件" + channelFile.getName() + errorInfo + "!，文件移至错误文件夹失败！");
	}
    }

    public static void moveErrorTxtFile(String timeStr, Map<String, String> sourceFiles_pkg, String errorInfo) {
	Iterator<String> keySetIterator = sourceFiles_pkg.keySet().iterator();
	while (keySetIterator.hasNext()) {
	    File file = new File(sourceFiles_pkg.get(keySetIterator.next()));
	    File destFile = new File(PropValue.getPropValue("error_location")
		    + file.getName().substring(0, file.getName().length() - ".txt".length()) + "_error" + "." + timeStr
		    + ".txt");
	    if (TxtFileOperation.copyFile(file, destFile, true)) {
		log.info("【渠道自动导入】 警告：" + errorInfo + "，源文件" + file.getName() + "已移入错误文件夹中！");
	    } else {
		log.info("【渠道自动导入】 警告：" + errorInfo + "，源文件" + file.getName() + "移入错误文件夹出错，请确认！");
	    }
	}
    }

    public static void moveErrorTxtFile(String timeStr, File channelFile, String errorInfo) {
	File destFile = new File(PropValue.getPropValue("error_location")
		+ channelFile.getName().substring(0, channelFile.getName().length() - ".txt".length()) + "_error" + "."
		+ timeStr + ".txt");
	if (TxtFileOperation.copyFile(channelFile, destFile, true)) {
	    log.info("【渠道自动导入】 警告：文件" + channelFile.getName() + errorInfo + "!，文件已成功移至错误文件夹！");
	} else {
	    log.info("【渠道自动导入】 警告：文件" + channelFile.getName() + errorInfo + "!，文件移至错误文件夹失败！");
	}
    }

    /**
     * 错误文件文件移动（通用版）
     * @param errorPath
     * @param timeStr
     * @param file
     * @param errorInfo
     */
    public static void rmErrorTxt(String errorPath, String timeStr, File file, String errorInfo) {
	File destFile = new File(errorPath + file.getName().substring(0, file.getName().length() - ".txt".length())
		+ "_error" + "." + timeStr + ".txt");
	if (TxtFileOperation.copyFile(file, destFile, true)) {
	    log.info("【渠道自动导入】 警告：文件" + file.getName() + errorInfo + "!，文件已成功移至错误文件夹！");
	} else {
	    log.info("【渠道自动导入】 警告：文件" + file.getName() + errorInfo + "!，文件移至错误文件夹失败！");
	}
    }

    /**
     * 错误文件文件批量移动（通用版）
     * @param errorPath
     * @param timeStr
     * @param sourceFilesRatio
     * @param errorInfo
     */
    public static void rmErrorTxt(String errorPath, String timeStr, Map<String, String> sourceFilesRatio,
	    String errorInfo) {
	Iterator<String> keySetIterator = sourceFilesRatio.keySet().iterator();
	while (keySetIterator.hasNext()) {
	    File file = new File(sourceFilesRatio.get(keySetIterator.next()));
	    File destFile = new File(errorPath + file.getName().substring(0, file.getName().length() - ".txt".length())
		    + "_error" + "." + timeStr + ".txt");
	    if (TxtFileOperation.copyFile(file, destFile, true)) {
		log.info("【渠道自动导入】 警告：" + errorInfo + "，源文件" + file.getName() + "已移入错误文件夹中！");
	    } else {
		log.info("【渠道自动导入】 警告：" + errorInfo + "，源文件" + file.getName() + "移入错误文件夹出错，请确认！");
	    }
	}

    }
}
