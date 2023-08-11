package com.flowengine.server.env;

/**
 * @author yangzl 2021.05.13
 * @version 1.00.00
 * @Description:
 * @history:
 */
public class FlowException extends RuntimeException{

    public FlowException() {
        super();
    }

    public FlowException(String message) {
        super(message);
    }
}
