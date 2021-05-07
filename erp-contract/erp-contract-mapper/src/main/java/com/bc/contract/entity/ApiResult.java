package com.bc.contract.entity;

import java.util.Map;

/**
 * 电子合同API返回值
 *
 * @author zhou
 */
public class ApiResult {

    private String message;
    private int code;
    private boolean success;
    private Map<String, Object> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ApiResult{" +
                "message='" + message + '\'' +
                ", code=" + code +
                ", success=" + success +
                ", data=" + data +
                '}';
    }
}
