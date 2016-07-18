package edu.galileo.android.taxiseguro.domain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by rodrigo on 15/06/16.
 */
public class AvatarHelper {
    private final static String GRAVATAR_URL = "http://www.gravatar.com/avatar/";

    public static String getAvatarUrl(String email){
        return GRAVATAR_URL + md5(email) + "?s=72";
    }

    private static final String md5(final String s){
        final String MD5 = "MD5";
        try{
            //crea el Hash md5
            MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            //Crea el String Hex
            StringBuilder hexString = new StringBuilder();
            for(byte aMessageDIgest : messageDigest){
                String h = Integer.toHexString(0XFF & aMessageDIgest);
                while(h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return "";
    }
}
