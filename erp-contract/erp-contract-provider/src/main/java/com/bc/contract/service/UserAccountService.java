package com.bc.contract.service;

import com.bc.contract.entity.UserAccount;

/**
 * 个人签署账号
 *
 * @author zhou
 */
public interface UserAccountService {

    /**
     * 新增个人签署账号
     *
     * @param userAccount 个人签署账号
     */
    void addUserAccount(UserAccount userAccount);

    /**
     * e签宝平台上创建个人签署账号
     *
     * @param token       token
     * @param userAccount 个人签署账号
     * @return e签宝平台上个人签署账号ID
     */
    String addUserAccountToSignPlatform(String token, UserAccount userAccount);

}
