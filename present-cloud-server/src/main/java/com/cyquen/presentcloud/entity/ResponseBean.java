package com.cyquen.presentcloud.entity;

/**
 * @author Zhengxikun
 */
public class ResponseBean {

    private Integer status;
    private String msg;
    private Object data;

    public static ResponseBean build() {
        return new ResponseBean();
    }

    public static ResponseBean ok(String msg) {
        return new ResponseBean(200, msg, null);
    }

    public static ResponseBean ok(String msg, Object data) {
        return new ResponseBean(200, msg, data);
    }

    public static ResponseBean error(String msg) {
        return new ResponseBean(500, msg, null);
    }

    public static ResponseBean error(String msg, Object data) {
        return new ResponseBean(500, msg, data);
    }

    private ResponseBean() {
    }

    private ResponseBean(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public ResponseBean setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResponseBean setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return data;
    }

    public ResponseBean setObj(Object data) {
        this.data = data;
        return this;
    }
}
