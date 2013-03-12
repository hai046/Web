package com.taobaoke.www.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class Test {

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
