package cn.migu.income.util;

import java.io.InputStream;
import java.text.DecimalFormat;

public class GetFileSize {
	
	public static boolean checkFileSize(InputStream inputStream) throws Exception {
		long s = inputStream.available();
		
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (s < 1024) {
			return false;
		} else if (s < 1048576) {
			return false;
		} else if (s < 1073741824) {
			fileSizeString = df.format((double) s / 1048576);
			if(Double.parseDouble(fileSizeString)>20d){
				return true;
			}else{
				return false;
			}
		} else {
			return true;
		}
	}
}
