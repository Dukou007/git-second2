package com.example.vo;

public class Mess {

    private int code;
    private String message;
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static Mess succ(Object data) {
        Mess mess = new Mess();
        mess.code = 1;
        mess.message = "操作成功";
        mess.data = data;

        return mess;
    }

    public static Mess error (String message) {
        Mess mess = new Mess();
        mess.code = -1;
        mess.message = message;
        mess.data = null;

        return mess;
    }
}
