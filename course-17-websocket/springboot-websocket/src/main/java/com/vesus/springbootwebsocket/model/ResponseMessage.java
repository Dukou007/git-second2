package com.vesus.springbootwebsocket.model;

/**
 * @Description:
 * @Author: vesus
 * @CreateDate: 2018/5/28 下午5:47
 * @Version: 1.0
 */
public class ResponseMessage {

    public ResponseMessage(String message) {
        this.message = message;
    }

    /**
     * 响应消息
     */
    private String message ;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
