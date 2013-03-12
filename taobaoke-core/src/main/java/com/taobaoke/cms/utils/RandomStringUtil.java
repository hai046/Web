package com.taobaoke.cms.utils;

import java.util.Random;

public class RandomStringUtil {
	private static char[] CHARS = "1234567890abcdefghigklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	
	public static String getRadmonStr(int length) {
		StringBuilder sb = new StringBuilder(length);
		Random r = new Random( System.nanoTime() );
		for( int i = 0; i < length; i ++ ){
			sb.append(CHARS[ r.nextInt(CHARS.length) ]);
		}
		return sb.toString();
	}
	
	public static void main(String []args){
		System.out.println( RandomStringUtil.getRadmonStr(16) );
	}
}