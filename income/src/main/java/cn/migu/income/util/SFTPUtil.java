package cn.migu.income.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class SFTPUtil {	
	
	private JSch jsch ;
	private Session sshSession ;
	private Channel channel ;
	private ChannelSftp sftp ;
    private String strIp;  
    private int intPort;  
    private String userName;  
    private String password;

    private static Logger logger = Logger.getLogger(SFTPUtil.class.getName());
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		
		SFTPUtil Util = new SFTPUtil("192.168.129.156", 22, "bossjs", "bossjs123") ;
		
		
		Vector<LsEntry> LsVercor =  Util.listFiles("/") ;
		
		Iterator<LsEntry>  it = LsVercor.iterator();
		
		while(it.hasNext()){
			LsEntry entry = ((LsEntry)it.next()) ;
			if(entry.getLongname().startsWith("d")){
				System.out.println(entry.getFilename()+entry.getFilename().length());
			}
		}

	}
	
	
	
	
	 public SFTPUtil(String strIp, int intPort, String userName, String password) {
		super();
		this.strIp = strIp;
		this.intPort = intPort;
		this.userName = userName;
		this.password = password;
		
		this.sftp = connect(strIp, intPort, userName, password) ;
		
		if(sftp != null ){
			logger.info("Sftp  连接成功！！");
		}else{
			logger.error("Sftp 连接失败！！");
		}
	}




	/** 
     * 连接sftp服务器 
     * @param host 主机 
     * @param port 端口 
     * @param username 用户名 
     * @param password 密码 
     * @return 
     */  
    public ChannelSftp connect(String host, int port, String username,  
            String password) {  
        try {  
            jsch = new JSch();  
            jsch.getSession(username, host, port);  
            sshSession = jsch.getSession(username, host, port);  
            logger.info("Sftp session 连接成功！"); 
            sshSession.setPassword(password);  
            Properties sshConfig = new Properties();  
            sshConfig.put("StrictHostKeyChecking", "no");  
            sshSession.setConfig(sshConfig);  
            sshSession.connect();  
            channel = sshSession.openChannel("sftp");  
            channel.connect();  
            sftp = (ChannelSftp) channel;  
            logger.info("连接到 " + host + ".");  
        } catch (Exception e) {  
            e.printStackTrace() ;  
        }  
        return sftp;  
    }  
  
    /** 
     * 上传文件 
     * @param directory 上传的目录 
     * @param uploadFile 要上传的文件 
     * @param sftp 
     */  
    public void upload(String directory, String uploadFile, ChannelSftp sftp) {  
        try {  
            sftp.cd(directory);  
            File file=new File(uploadFile);  
            sftp.put(new FileInputStream(file), file.getName());  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    /** 
     * 下载文件 
     * @param directory 下载目录 
     * @param downloadFile 下载的文件 
     * @param saveFile 存在本地的路径 
     * @param sftp 
     */  
    public void download(String directory, String downloadFile,String saveFile) {  
    	File file=new File(saveFile); 
    	FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
        try {  
            sftp.cd(directory);  
             
            sftp.get(downloadFile, fos); 
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        
        if(fos != null ){
        	try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }  
  
    /** 
     * 删除文件 
     * @param directory 要删除文件所在目录 
     * @param deleteFile 要删除的文件 
     * @param sftp 
     */  
    public void delete(String directory, String deleteFile) {  
        try {  
            sftp.cd(directory);  
            sftp.rm(deleteFile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    /** 
     * 列出目录下的文件 
     * @param directory 要列出的目录 
     * @param sftp 
     * @return 
     * @throws SftpException 
     */    
    @SuppressWarnings("unchecked")  
    public Vector<LsEntry> listFiles(String directory){  
    	Vector<LsEntry> v = new Vector<>();
        try {
        	v = sftp.ls(directory);  
        }catch(SftpException e){
        	logger.info(directory+"   路径不存在");
        }
        
        return v ;
    }  
    
    
    /**
     * 关闭连接！！
     */
    public void close(){
    	
    	if(this.sftp != null ){
    		this.sftp.disconnect();
    	}
    	
    	if(this.channel != null ){
    		this.channel.disconnect();
    	}
    	
    	if(this.sshSession != null ){
    		this.sshSession.disconnect();
    	}
    	
    	logger.info("Disconnect  SFTP！！");
    }

}
