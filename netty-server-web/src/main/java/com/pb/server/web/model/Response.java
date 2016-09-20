package com.pb.server.web.model;

/**
 * Created by piecebook on 2016/9/20.
 */
public class Response {
    public static final int SUCCESS = 200;
    public static final int SERVER_ERROR = 500;
    public static final int ARGS_ERROR = 400;
    public static final int METHOD_UNSUPORT = 405;


    private int error_code;
    private String reason;
    private Object data;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Response error(int error_code, String reason) {
        this.error_code = error_code;
        this.reason = reason;
        return this;
    }

    public Response success(Object data){
        this.error_code = SUCCESS;
        this.reason = "success";
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "Response{" +
                "error_code=" + error_code +
                ", reason='" + reason + '\'' +
                ", data=" + data +
                '}';
    }
}
