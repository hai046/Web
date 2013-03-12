package com.taobaoke.www.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class Test {
    
    private static Map<String, Integer> hash = new HashMap<String, Integer>();
    private static int[] firstCharCheck = new int[Character.MAX_VALUE];
    private static int[] allCharCheck = new int[Character.MAX_VALUE];        
    private static int maxlength = 0;
    private static String[] badwords = "操你妈,干你,我日".split(",");
    
    static{
        for(String badword : badwords){
            if (!hash.containsKey(badword)){
                hash.put(badword, 0);
                maxlength = Math.max(maxlength, badword.length());
//                firstCharCheck[word[0]] = true;
//    
//                foreach (char c in word)
//                {
//                    allCharCheck[c] = true;
//                }
            }
        }
    }
    
    public static void main(String []args){
        try {
            
//            String content = "120605181003";
//            String path = "/Users/koutosei/Desktop/";
//            
//            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
//            
//            Map<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
//            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
//            BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400,hints);
//            File file1 = new File(path,"test123123123.jpg");
//            MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file1);
            
            Map<String, Object> hei = new HashMap<String, Object>();
            hei.put("a", null);
            System.out.println( hei.get("hei") == null );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
