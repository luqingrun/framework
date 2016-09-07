package com.jiuxian.framework.util.page;

public class AjaxResponseData implements java.io.Serializable{
    private static final long serialVersionUID = 6791974967234726972L;
    //自定义状态码,非http状态码
    private int code = 200;

    private String msg = "";

    private Object data = "";

    public AjaxResponseData(){
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
