package com.bc.contract.mapper;

import com.bc.contract.entity.UserAccount;

/**
 * 个人签署账号
 *
 * @author zhou
 */
public interface UserAccountMapper {

    /**
     * 新增个人签署账号
     *
     * @param userAccount 个人签署账号
     */
    void addUserAccount(UserAccount userAccount);

    /**
     * 通过签署账号ID删除签署账号
     *
     * @param accountId 签署账号ID
     */
    void deleteUserAccountByAccountId(String accountId);
}
