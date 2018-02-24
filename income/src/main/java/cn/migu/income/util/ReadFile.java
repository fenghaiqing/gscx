package cn.migu.income.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ReadFile {
	
	private InterfaceTest interfaceTest ;
	
	private List<String>  contentList ;
	
	private File file = null ;
	
	private  int size = 0;
	
	private boolean  finshFlag = false ;
	
	private BufferedReader buffReader = null;
	
	private InputStreamReader inputStreamReader = null ;
	
	/**
	 * @param filePath
	 */
	
	public ReadFile(String filePath,InterfaceTest i){
		interfaceTest = i ;
		if(!filePath.isEmpty()&&(filePath != null)){
			this.file = new File(filePath);
			
			this.contentList = new ArrayList<String>();
			
			if(file.isFile() && file.exists()){
				try {
					inputStreamReader = new InputStreamReader(new FileInputStream(file),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				 buffReader = new BufferedReader(inputStreamReader);
			}
		}
	}
	
	/**
	 * 
	 * @param filePath
	 * @param size  
	 */
	public ReadFile(String filePath , int size,InterfaceTest i){
		interfaceTest = i ;
		if(!filePath.isEmpty()&&(filePath != null)){
			this.file = new File(filePath);
			
			this.contentList = new ArrayList<String>();
			
			this.size = size ;
			
			if(file.isFile() && file.exists()){
				try {
					inputStreamReader = new InputStreamReader(new FileInputStream(file),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				 buffReader = new BufferedReader(inputStreamReader);
			}
		}
	}
	
	
	public boolean next(){
		String lineTxt = null;
		if(size == 0){
			contentList.clear();
            try {
				while((lineTxt = buffReader.readLine()) != null){
					contentList.add(lineTxt) ;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
            this.setFinshFlag(true);
            interfaceTest.method(contentList);
            clear();
		}else{
			contentList.clear();
			int hasReadLine = 0 ;
			try {
				while(hasReadLine<size){
					lineTxt = buffReader.readLine() ;
					if(lineTxt != null){
						contentList.add(lineTxt) ;
						hasReadLine++ ;
					}else{
						break ;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(contentList.size() < size){
				 this.setFinshFlag(true);
				 clear();
				 if(contentList.size() != 0){
					 interfaceTest.method(contentList);
				 }
			}else{
				 this.setFinshFlag(false);
				 interfaceTest.method(contentList);
			}
		}
		return this.finshFlag;
	}
	
	private void clear(){
		if(buffReader != null || inputStreamReader != null){
			try {
				buffReader.close();
				inputStreamReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public boolean isFinsh(){
		return this.finshFlag ;
	}


	public List<String> getContentList() {
		return contentList;
	}


	public void setContentList(List<String> contentList) {
		this.contentList = contentList;
	}


	public File getFile() {
		return file;
	}


	public void setFile(File file) {
		this.file = file;
	}


	public int getSize() {
		return size;
	}


	public void setSize(int size) {
		this.size = size;
	}


	public boolean isFinshFlag() {
		return finshFlag;
	}


	public void setFinshFlag(boolean finshFlag) {
		this.finshFlag = finshFlag;
	}


	public BufferedReader getBuffReader() {
		return buffReader;
	}


	public void setBuffReader(BufferedReader buffReader) {
		this.buffReader = buffReader;
	}
	
	
	public interface InterfaceTest {
		/**
		 *   
		 * @param list
		 */
		public void method(List<String>  list);
		
	}
}
