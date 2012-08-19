package com.map.gourmet.activity;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.util.Base64;

public class BaseActivity  extends Activity{
	protected String test = "aaa";
	
	protected boolean httpTransfar(String userId, String passwd) throws Exception {
    	HttpURLConnection conn = null;
    	try {
	    	URL url = new URL("http://192.168.11.2:3000/users");
	    	conn = (HttpURLConnection)url.openConnection();
	    	conn.setRequestMethod("GET");
	    	String arg = commonsBase64(userId + ":" + passwd);
	    	conn.setRequestProperty("Authorization", arg);	    	
	    	conn.setDoOutput(true);
	    	conn.getInputStream();
	    	conn.disconnect();
	    	return true;
    	} catch(Exception e) {
    		if ( conn != null ) {
        		Integer a = conn.getResponseCode();
        		if ( a == 401 ) {
        			System.out.println("認証チェックエラー");
        			return false;
        		}
    		}
    		e.printStackTrace();
    	}
		return true;		
	}
	public String commonsBase64(String str) {
	  byte[] encoded = new byte[0];
      String encodedStr = "";
      try {
    	encoded = Base64.encode(str.getBytes("UTF-8"), android.util.Base64.URL_SAFE|android.util.Base64.NO_WRAP);
        encodedStr = new String(encoded);
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
      return encodedStr;
    }
	

}
