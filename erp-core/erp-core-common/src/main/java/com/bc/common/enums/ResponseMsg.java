package com.bc.common.enums;

/**
 * 返回消息
 *
 * @author zhou
 */
public enum ResponseMsg {

    /**
     * 返回信息
     */
    ESIGN_CONFIG_EMPTY("ESIGN_CONFIG_EMPTY", "电子合同API未做配置"),
    ADD_SUCCESS("ADD_SUCCESS", "新增成功"),
    ADD_ERROR("ADD_ERROR", "新增失败"),

    DELETE_SUCCESS("DELETE_SUCCESS", "删除成功"),
    DELETE_ERROR("DELETE_ERROR", "删除失败"),
    ;

    ResponseMsg(String responseCode, String responseMessage) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    private String responseCode;
    private String responseMessage;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

}
