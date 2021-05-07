package com.bc.contract.enums;

/**
 * 电子合同API响应参数
 *
 * @author zhou
 */
public enum ApiResultEnum {

    /**
     * API响应
     */
    OK(0, "成功"),
    ;

    /**
     * 接口响应结果码
     */
    private int code;

    /**
     * 接口响应结果描述
     */
    private String message;

    ApiResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

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

}
