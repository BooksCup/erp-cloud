package com.bc.contract.entity;

import com.bc.common.utils.PrimaryKeyUtil;

/**
 * 个人签署账号
 *
 * @author zhou
 */
public class UserAccount {

    private String id;
    private String name;
    private String idType;
    private String idNumber;
    private String mobile;
    private String email;

    public UserAccount() {

    }

    public UserAccount(String name, String idType, String idNumber, String mobile, String email) {
        this.id = PrimaryKeyUtil.generateId();
        this.name = name;
        this.idType = idType;
        this.idNumber = idNumber;
        this.mobile = mobile;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
