package com.bc.contract.service.impl;

import com.bc.contract.entity.UserAccount;
import com.bc.contract.mapper.UserAccountMapper;
import com.bc.contract.service.UserAccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 个人签署账号
 *
 * @author zhou
 */
@Service("userAccountService")
public class UserAccountServiceImpl implements UserAccountService {

    @Resource
    private UserAccountMapper userAccountMapper;

    /**
     * 新增个人签署账号
     *
     * @param userAccount 个人签署账号
     */
    @Override
    public void addUserAccount(UserAccount userAccount) {
        userAccountMapper.addUserAccount(userAccount);
    }

}
