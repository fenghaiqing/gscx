package cn.migu.income.util;  
  
import java.io.BufferedInputStream;  
import java.io.BufferedOutputStream;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.FileOutputStream;  
import java.io.IOException;
import java.util.ArrayList;
import java.util.TimeZone;  
import org.apache.commons.net.ftp.FTPClient;  
import org.apache.commons.net.ftp.FTPClientConfig;  
import org.apache.commons.net.ftp.FTPFile;  
import org.apache.commons.net.ftp.FTPReply;  
  
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

  
public class Ftp {  
	private static Ftp ftp;
    private FTPClient ftpClient;  
    private String strIp;  
    private int intPort;  
    private String user;  
    private String password;
    public ArrayList<String> arFiles;
   
    private static Logger logger = Logger.getLogger(Ftp.class.getName());
    
    public static Ftp getInstance(String strIp, int intPort, String user, String Password){
    		ftp = new Ftp(strIp,intPort,user,Password);
    	return ftp;
    }
  
    /* * 
     * Ftp构造函数 
     */  
    private Ftp(String strIp, int intPort, String user, String Password) {  
        this.strIp = strIp;  
        this.intPort = intPort;  
        this.user = user;  
        this.password = Password;  
        this.ftpClient = new FTPClient();
        this.arFiles = new ArrayList<String>() ;
    }  
    /** 
     * @return 判断是否登入成功 
     * */  
    public boolean ftpLogin() {  
        boolean isLogin = false;  
        FTPClientConfig ftpClientConfig = new FTPClientConfig();  
        ftpClientConfig.setServerTimeZoneId(TimeZone.getDefault().getID());  
        this.ftpClient.setControlEncoding("GBK");  
        this.ftpClient.configure(ftpClientConfig);  
        try {  
            if (this.intPort > 0) {  
                this.ftpClient.connect(this.strIp, this.intPort);  
            } else {  
                this.ftpClient.connect(this.strIp);  
            }  
            // FTP服务器连接回答  
            int reply = this.ftpClient.getReplyCode();  
            if (!FTPReply.isPositiveCompletion(reply)) {  
                this.ftpClient.disconnect();  
                logger.error("FTP服务异常！");  
                return isLogin;  
            }  
            boolean result = this.ftpClient.login(this.user, this.password); 
            if(result){
            	logger.info("恭喜" + this.user + "成功登陆FTP服务器"); 
            	// 设置传输协议  
                this.ftpClient.enterLocalPassiveMode();  
                this.ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);  
                isLogin = true; 
                this.ftpClient.setBufferSize(1024 * 2);  
                this.ftpClient.setDataTimeout(300 * 1000); 
            }else{
            	logger.info("" + this.user + "登陆FTP服务器失败！");
            	logger.info("请核对！ 账号：" + this.user + "  密码："+this.password);
            	isLogin = false;
            }
             
        } catch (Exception e) {  
            e.printStackTrace();  
            logger.error(this.user + "登录FTP服务失败！" + e.getMessage());  
        }  
        return isLogin;  
    }  
  
    /** 
     * @退出关闭服务器链接 
     * */  
    public void ftpLogOut() {  
        if (null != this.ftpClient && this.ftpClient.isConnected()) {  
            try {  
                boolean reuslt = this.ftpClient.logout();// 退出FTP服务器  
                if (reuslt) {  
                    logger.info("成功退出服务器");  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
                logger.warn("退出FTP服务器异常！" + e.getMessage());  
            } finally {  
                try {  
                    this.ftpClient.disconnect();// 关闭FTP服务器的连接  
                } catch (IOException e) {  
                    e.printStackTrace();  
                    logger.warn("关闭FTP服务器的连接异常！");  
                }  
            }  
        }  
    }  
  
    /*** 
     * 上传Ftp文件 
     * @param localFile 当地文件 
     * @param romotUpLoadePath上传服务器路径 - 应该以/结束 
     * */  
    public boolean uploadFile(File localFile, String romotUpLoadePath) {  
        BufferedInputStream inStream = null;  
        boolean success = false;  
        try {  
            this.ftpClient.changeWorkingDirectory(romotUpLoadePath);// 改变工作路径  
            inStream = new BufferedInputStream(new FileInputStream(localFile));  
            logger.info(localFile.getName() + "开始上传.....");  
            success = this.ftpClient.storeFile(localFile.getName(), inStream);  
            if (success == true) {  
                logger.info(localFile.getName() + "上传成功");  
                return success;  
            }  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
            logger.error(localFile + "未找到");  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (inStream != null) {  
                try {  
                    inStream.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        return success;  
    }  
    
    
    
    public boolean uploadFile2(File localFile, String romotUpLoadePath,String remoteFileName) {  
        BufferedInputStream inStream = null;  
        boolean success = false;  
        try {  
            this.ftpClient.changeWorkingDirectory(romotUpLoadePath);// 改变工作路径  
            inStream = new BufferedInputStream(new FileInputStream(localFile));  
            logger.info(localFile.getName() + "开始上传.....");  
            success = this.ftpClient.storeFile(remoteFileName, inStream);  
            if (success == true) {  
                logger.info(remoteFileName + "上传成功");  
                return success;  
            }  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
            logger.error(localFile + "未找到");  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (inStream != null) {  
                try {  
                    inStream.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        return success;  
    }
  
    /*** 
     * 下载文件 
     * @param remoteFileName   待下载文件名称 
     * @param localDires 下载到当地那个路径下 
     * @param remoteDownLoadPath remoteFileName所在的路径 
     * */  
  
    public boolean downloadFile(String remoteFileName, String localDires,  
            String remoteDownLoadPath) {  
        String strFilePath = localDires + remoteFileName;  
        BufferedOutputStream outStream = null;  
        boolean success = false;  
        try {  
            this.ftpClient.changeWorkingDirectory(remoteDownLoadPath);  
            outStream = new BufferedOutputStream(new FileOutputStream(  
                    strFilePath));  
            logger.info(remoteFileName + "开始下载....");  
            success = this.ftpClient.retrieveFile(remoteFileName, outStream);  
            if (success == true) {  
                logger.info(remoteFileName + "成功下载到" + strFilePath);  
                return success;  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
            logger.error(remoteFileName + "下载失败");  
        } finally {  
            if (null != outStream) {  
                try {  
                    outStream.flush();  
                    outStream.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        if (success == false) {  
            logger.error(remoteFileName + "下载失败!!!");  
        }  
        return success;  
    }  
    
    
    /*** 
     * 下载文件 
     * @param fileNameList   待下载文件名称列表 
     * @param localDires 下载到当地那个路径下 
     * @param remoteDownLoadPath remoteFileName所在的路径 
     * @param isDeleteRemoteFile 下载完成是否删除远程路径的文件
     * */  
    public boolean downloadFiles(ArrayList<String> fileNameList, String localDires,  
            String remoteDownLoadPath,boolean isDeleteRemoteFile) {
    			int num = 0;
    			ArrayList<String>  hasDownloadFileList = new ArrayList<>();
    			for(String tempName : fileNameList){
    				if(downloadFile(tempName, localDires, remoteDownLoadPath)){
    					num++ ;
    					hasDownloadFileList.add(tempName);
    				}
    			}
    			
				if(num == fileNameList.size()){
					logger.info("下载完成，总共下载"+num+"个文件");
					if(isDeleteRemoteFile){
						try {
							deleteFiles(remoteDownLoadPath, hasDownloadFileList);
						} catch (IOException e) {
							logger.info("Error deleteFiles!");
							e.printStackTrace();
						}
					}
					return true ;
				}else{
					//清除所下载的文件
					logger.info("下载未完成，总共下载"+num+"个文件");
					return false;
				}
				
    } 
  
    /*** 
     * @上传文件夹 
     * @param localDirectory 
     *            当地文件夹 
     * @param remoteDirectoryPath 
     *            Ftp 服务器路径 以目录"/"结束 
     * */  
    public boolean uploadDirectory(String localDirectory,  
            String remoteDirectoryPath) {  
        File src = new File(localDirectory);  
        try {  
            remoteDirectoryPath = remoteDirectoryPath + src.getName() + "/";  
            this.ftpClient.makeDirectory(remoteDirectoryPath);  
        } catch (IOException e) {  
            e.printStackTrace();  
            logger.info(remoteDirectoryPath + "目录创建失败");  
        }  
        File[] allFile = src.listFiles();  
        for (int currentFile = 0; currentFile < allFile.length; currentFile++) {  
            if (!allFile[currentFile].isDirectory()) {  
                String srcName = allFile[currentFile].getPath().toString();  
                uploadFile(new File(srcName), remoteDirectoryPath);  
            }  
        }  
        for (int currentFile = 0; currentFile < allFile.length; currentFile++) {  
            if (allFile[currentFile].isDirectory()) {  
                // 递归  
                uploadDirectory(allFile[currentFile].getPath().toString(),  
                        remoteDirectoryPath);  
            }  
        }  
        return true;  
    }  
  
    /*** 
     * @下载文件夹 
     * @param localDirectoryPath本地地址 
     * @param remoteDirectory 远程文件夹 
     * */  
    public boolean downLoadDirectory(String localDirectoryPath,String remoteDirectory) {  
        try {  
            String fileName = new File(remoteDirectory).getName();  
            localDirectoryPath = localDirectoryPath + fileName + "//";  
            new File(localDirectoryPath).mkdirs();  
            FTPFile[] allFile = this.ftpClient.listFiles(remoteDirectory);  
            for (int currentFile = 0; currentFile < allFile.length; currentFile++) {  
                if (!allFile[currentFile].isDirectory()) {  
                    downloadFile(allFile[currentFile].getName(),localDirectoryPath, remoteDirectory);  
                }  
            }  
            for (int currentFile = 0; currentFile < allFile.length; currentFile++) {  
                if (allFile[currentFile].isDirectory()) {  
                    String strremoteDirectoryPath = remoteDirectory + "/"+ allFile[currentFile].getName();  
                    downLoadDirectory(localDirectoryPath,strremoteDirectoryPath);  
                }  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
            logger.info("下载文件夹失败");  
            return false;  
        }  
        return true;  
    }
    
    /** 
     * 递归遍历目录下面指定的文件名 
     * @param pathName 需要遍历的目录，必须以"/"开始和结束 
     * @param ext 文件的扩展名 
     * @throws IOException  
     */  
    public void listFilesRecursive (String pathName,String ext) throws IOException{  
        if(pathName.startsWith("/")&&pathName.endsWith("/")){  
            String directory = pathName;  
            //更换目录到当前目录  
            this.ftpClient.changeWorkingDirectory(directory);  
            //清空目录
            this.arFiles.clear();
            FTPFile[] files = this.ftpClient.listFiles();  
            for(FTPFile file:files){ 
                if(file.isFile()){  
                    if(file.getName().endsWith(ext)){  
                        arFiles.add(directory+file.getName());  
                    }  
                }else if(file.isDirectory()){  
                	listFilesRecursive(directory+file.getName()+"/",ext);  
                }  
            }  
        }  
    }  
    
    
    /** 
     * 遍历当前目录下面指定的文件名 
     * @param pathName 需要遍历的目录，必须以"/"开始和结束 
     * @param ext 文件的扩展名 
     * @throws IOException  
     */  
    public ArrayList<String> listFilesCurrent (String pathName) throws IOException{  
    	ArrayList<String> fileNameList = new ArrayList<String>();
    	if(pathName.endsWith("/")){  
            String directory = pathName;  
            //更换目录到当前目录  
            this.ftpClient.changeWorkingDirectory(directory);
            FTPFile[] files = this.ftpClient.listFiles();  
            for(FTPFile file:files){ 
                if(file.isFile()){  
                	fileNameList.add(file.getName());  
                } 
            }  
        }
        return fileNameList;
    }  
    
    
    public boolean deleteFile(String pathName,String fileName) throws IOException{
    	boolean flag = false;
    	if(pathName.endsWith("/")){
    		String directory = pathName;  
            //更换目录到当前目录  
            this.ftpClient.changeWorkingDirectory(directory);
            flag = this.ftpClient.deleteFile(fileName);
            if(flag){
            	 logger.info("文件"+fileName+"删除成功！");
            	 return flag;
            }else{
            	logger.info("文件"+fileName+"删除失败！");
            	 return flag;
            }
    	}else{
    		logger.info("文件目录不正确！");
    		return flag;
    	}
    }
    
    public void deleteFiles(String pathName,ArrayList<String> fileNames) throws IOException{
//    	if(pathName.startsWith("/")&&pathName.endsWith("/")){
    	if(pathName.endsWith("/")){
    		String directory = pathName;  
            //更换目录到当前目录  
            this.ftpClient.changeWorkingDirectory(directory);
            for(String fileName : fileNames){
            	ftp.deleteFile(pathName,fileName);
            }
            logger.info("删除多个文件成功！");	
    	}else{
    		logger.info("文件目录不正确！");
    	}
    }
    
    /**
     * 根据路径判断FTP上是否存在这个目录
     * @param path
     * @return
     */
    public boolean exists(String path){
    	boolean result = false ;
    	try {
			result = this.ftpClient.changeWorkingDirectory(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	if(result){
    		logger.info(path+"存在,验证成功！");
    		try {
				this.ftpClient.changeWorkingDirectory("/");
			} catch (IOException e) {
				e.printStackTrace();
			}
    		logger.info("切换至根目录"+"/");
    	}else{
    		logger.info(path+"不存在,验证失败！");
    	}
    	return result ;
    }
    
    // FtpClient的Set 和 Get 函数  
    public FTPClient getFtpClient() {  
        return ftpClient;  
    }  
    public void setFtpClient(FTPClient ftpClient) {  
        this.ftpClient = ftpClient;  
    }  
       
}