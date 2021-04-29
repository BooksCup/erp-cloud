package com.bc.contract.entity;

/**
 * 电子合同API配置
 *
 * @author zhou
 */
public class ContractApiConfig {

    /**
     * 应用id
     */
    private String appId;

    /**
     * 应用密钥
     */
    private String secret;

    /**
     * 授权类型，固定值: client_credentials
     */
    private String grantType = "client_credentials";

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

}
