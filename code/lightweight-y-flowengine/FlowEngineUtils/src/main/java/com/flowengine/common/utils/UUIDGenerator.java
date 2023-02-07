package com.flowengine.common.utils;

import java.util.UUID;


/**
 * @Description:
 * @author yangzl 2019-07-10
 * @version 1.00.00
 * @history:
 */
public class UUIDGenerator {
    /** 
     * 获得一个UUID 
     * @return String UUID 
     */ 
    public static String getUUID(){ 
        String s = UUID.randomUUID().toString(); 
        //去掉“-”符号 
        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24); 
    } 
    
    public static void main(String[] args) {
    	for (int i = 0; i < 6; i++) {
    		System.out.println(UUIDGenerator.getUUID());
		}
	}
}
