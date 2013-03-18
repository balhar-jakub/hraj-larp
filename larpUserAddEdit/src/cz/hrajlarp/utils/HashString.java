package cz.hrajlarp.utils;

import java.security.MessageDigest;

public class HashString {
	
    public String digest(String password)throws Exception {
    	
    	MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(password.getBytes("UTF-8"));
		
        byte byteArr[] = digest.digest();			
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteArr.length; i++) {
        	sb.append(Integer.toString((byteArr[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}