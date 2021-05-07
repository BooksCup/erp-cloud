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

    /**
     * e签宝平台上注销个人签署账号
     *
     * @param token     token
     * @param accountId 个人签署账号ID
     * @return true: 注销成功   false: 注销失败
     */
    boolean deleteUserAccountFromSignPlatform(String token, String accountId);

    /**
     * 通过签署账号ID删除签署账号
     *
     * @param accountId 签署账号ID
     */
    void deleteUserAccountByAccountId(String accountId);

}
