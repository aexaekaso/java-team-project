package cafe;

import java.io.UnsupportedEncodingException;

public class PrintFormat {

	public int countByte(String str) {
	    int length = 0;
	    if (str != null) {
	        try { length = str.getBytes("euc-kr").length; } 
	        catch (UnsupportedEncodingException e) { }
	    }
	    return length;
	}
	
	public int convertByte(int formatSize, String str) {
	    return formatSize - (countByte(str) - str.length());
	}
	
}
