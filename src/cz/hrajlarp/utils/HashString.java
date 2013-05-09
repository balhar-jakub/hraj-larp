package cz.hrajlarp.utils;

import java.security.MessageDigest;
import java.security.SecureRandom;

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
    
    public String digestWithSalt(String password, String salt)throws Exception {

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        String saltedPass = salt+password;
        digest.update(saltedPass.getBytes("UTF-8"));

        byte byteArr[] = digest.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteArr.length; i++) {
            sb.append(Integer.toString((byteArr[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
    
    public String generateSalt(int length)
    {
    	SecureRandom rnd = new SecureRandom();
    	String saltChars="ab@$c@defgh*i1jklm$op#q%^rst4uvwx&y2z";
        char[] text = new char[length];
        
        for (int i = 0; i < length; i++)
            text[i] = saltChars.charAt(rnd.nextInt(saltChars.length()));
        
        return new String(text);
    }
}