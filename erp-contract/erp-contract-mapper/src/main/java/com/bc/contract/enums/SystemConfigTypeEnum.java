package com.bc.contract.enums;

/**
 * 系统配置类型
 *
 * @author zhou
 */
public enum SystemConfigTypeEnum {

    /**
     * 系统配置类型
     */
    ESIGN("ESIGN", "电子合同"),
    ;

    private String type;
    private String name;

    SystemConfigTypeEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
