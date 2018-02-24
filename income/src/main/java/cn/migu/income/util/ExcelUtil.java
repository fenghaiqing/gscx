package cn.migu.income.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

public class ExcelUtil {
    
	// 需要打包的ZIP文件集合
	private File srcPath;

	// ZIP或者Excel文件的名称
	private String outFilename;

	// 需要打包的ZIP文件长度
	private int len;
	
	// 当前的结算月份
    private String  accountMonth;

	public void setSrcPath(String src) {
		srcPath = new File(src);
	}

	public File getSrcPath() {
		return srcPath;
	}

	public void setOutFilename(String out) {
		outFilename = out;
	}

	public String getOutFilename() {
		return outFilename;
	}

	public String getAccountMonth()
    {
        return accountMonth;
    }

    public void setAccountMonth(String accountMonth)
    {
        this.accountMonth = accountMonth;
    }

    /**
	 * 将固定目录下的文件打包生成zip文件
     * @throws Exception 
	 * 
	 * @see [类、类#方法、类#成员]
	 */
	public void dozip() throws Exception {
		byte[] buf = new byte[1024];
		try {
			File[] files = srcPath.listFiles();
			len = srcPath.listFiles().length;
			String[] filenames = new String[len];
			for (int i = 0; i < len; i++) {
				// if(!files[i].isDirectory())
				filenames[i] = srcPath.getPath() + File.separator
						+ files[i].getName();
			}
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
					outFilename));
			for (int i = 0; i < filenames.length; i++) {
				FileInputStream in = new FileInputStream(filenames[i]);
				out.putNextEntry(new ZipEntry(files[i].getName()));
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}

				out.closeEntry();
				in.close();
			}

			out.close();
		} catch (Exception e) {
		    throw new Exception("文件打包生成zip文件异常，请检查！");
		}
	}
	
	/**
     *  将EXCEL推向页面展示
     * @param response
     * @param filePath
     * @param fileName
     * @throws Exception 
     * @see [类、类#方法、类#成员]
     */
    public void showExcelWindows(HttpServletResponse response, String filePath, String fileName) throws Exception
    {
        PrintStream ps = null;
        FileInputStream fis = null;
        try
        {
            // 将EXCEL推向页面展示
            ps = new java.io.PrintStream(response.getOutputStream());
            fis = new java.io.FileInputStream(filePath + "/" + fileName);
            response.reset();
            response.setContentType("application/msexcel");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO-8859-1"));
            
            int bytes_read = 0;
            byte[] buffer = new byte[1024];
            while ((bytes_read = fis.read(buffer)) > 0) {
                ps.write(buffer, 0, bytes_read);
            }
        }
        catch (Exception e)
        {
            throw new Exception("将EXCEL推向页面展示功能异常，请检查！");
        }
        finally 
        {
            try {
                fis.close();
            } catch (Exception e) {
            }
            try {
                ps.close();
            } catch (Exception e) {
            }
        }
    }
	
	/**
	 *  将ZIP推向页面展示
	 * @param response
	 * @param basePath
	 * @throws Exception 
	 * @see [类、类#方法、类#成员]
	 */
	public void showZipWindows(HttpServletResponse response, String basePath, String fileName) throws Exception
	{
	    PrintStream ps = null;
        FileInputStream fis = null;
        try
        {
            fileName = (null == fileName)?accountMonth : fileName;
            // 将打包的ZIP送到浏览器显示
            ps = new java.io.PrintStream(response.getOutputStream());
            fis = new java.io.FileInputStream(basePath + ".zip");
            response.reset();
            response.setContentType("application/ms-zip");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO-8859-1"));
            int bytes_read = 0;
            byte[] buffer = new byte[1024];
            while ((bytes_read = fis.read(buffer)) > 0) {
                ps.write(buffer, 0, bytes_read);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("将ZIP推向页面展示功能异常，请检查！");
        }
        finally 
        {
            try 
            {
                fis.close();
            } 
            catch (Exception e) 
            {
            }
            try 
            {
                ps.close();
            } 
            catch (Exception e) 
            {
            }
        }
	}
	
	/**
	 * 创建新的目录（如果存在原有目录，则删除后重新生成）
	 * @param basePath
	 * @throws Exception 
	 * @see [类、类#方法、类#成员]
	 */
	public boolean createDir(String basePath) throws Exception
	{
	    boolean hasCreateFlg = false;
	    try
        {
	        // 删除文件夹
	        delFolder(basePath);
	        File dirFile = new File(basePath);
	        if (!dirFile.exists()) 
	        {
	            hasCreateFlg = dirFile.mkdir();
	        }
        }
        catch (Exception e)
        {
            throw new Exception("创建新的目录异常");
        }
	    return hasCreateFlg;
	}
	
	/**
	 * 删除文件夹
	 * <功能详细描述>
	 * @param folderPath 文件夹完整绝对路径
	 * @see [类、类#方法、类#成员]
	 */
	private boolean delFolder(String folderPath) throws Exception
	{
    	delAllFile(folderPath); //删除完里面所有内容
    	File myFilePath = new File(folderPath);
    	return myFilePath.delete(); //删除空文件夹
	}
	
	/**
	 * 删除指定文件夹下所有文件
	 * <功能详细描述>
	 * @param path 文件夹完整绝对路径
	 * @return
	 * @throws Exception 
	 * @see [类、类#方法、类#成员]
	 */
	private boolean delAllFile(String path) throws Exception 
	{
    	boolean flag = false;
    	File file = new File(path);
    	if (!file.exists()) 
    	{
    	    return flag;
    	}
    	if (!file.isDirectory()) 
    	{
    	    return flag;
    	}
    	String[] tempList = file.list();
    	File temp = null;
    	for (int i = 0; i < tempList.length; i++) 
    	{
        	if (path.endsWith(File.separator)) 
        	{
        	    temp = new File(path + tempList[i]);
        	} 
        	else 
        	{
        	    temp = new File(path + File.separator + tempList[i]);
        	}
        	if (temp.isFile()) 
        	{
        	    flag = temp.delete();
        	}
        	if (temp.isDirectory()) 
        	{
            	delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
            	delFolder(path + "/" + tempList[i]);//再删除空文件夹
            	flag = true;
        	}
    	}
    	return flag;
	}
}
