package com.flowengine.common.utils;

/**
 * @Description:SQL异常
 * @author yangzl 2022-02-23
 * @version 1.00.00
 * @history:
 */
public class SQLException extends RuntimeException{

    public SQLException() {
        super();
    }

    public SQLException(String message) {
        super(message);
    }
}
