package org.example.manage.common;

import java.util.List;

public class Result {
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

    private int code;
    private String msg;
    private Object data;
    private String url;

    public Result() {
    }
    public Result(String msg) {
        this.msg = msg;
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(String url, String message) {
        this.url = url;
        this.msg = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public  static Result success() {
        return new Result(200,"success",null);
    }

    public  static Result success(Object data) {
        return new Result(200,"success",data);
    }

    public static Result error(Integer code,String msg) {
        return new Result(code,msg);
    }
}
