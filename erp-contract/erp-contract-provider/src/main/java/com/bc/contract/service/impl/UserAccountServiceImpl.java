package com.bc.contract.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bc.common.cons.Constant;
import com.bc.common.enums.ResponseMsg;
import com.bc.common.utils.HttpUtil;
import com.bc.contract.entity.ApiResult;
import com.bc.contract.entity.ContractApiConfig;
import com.bc.contract.entity.SystemConfig;
import com.bc.contract.entity.UserAccount;
import com.bc.contract.enums.SystemConfigTypeEnum;
import com.bc.contract.mapper.SystemConfigMapper;
import com.bc.contract.mapper.UserAccountMapper;
import com.bc.contract.service.UserAccountService;
import org.apache.http.client.methods.HttpHead;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 个人签署账号
 *
 * @author zhou
 */
@Service("userAccountService")
public class UserAccountServiceImpl implements UserAccountService {

    private static final Logger LOG = LoggerFactory.getLogger(UserAccountServiceImpl.class);

    @Resource
    private UserAccountMapper userAccountMapper;

    @Resource
    private SystemConfigMapper systemConfigMapper;

    /**
     * 新增个人签署账号
     *
     * @param userAccount 个人签署账号
     */
    @Override
    public void addUserAccount(UserAccount userAccount) {
        userAccountMapper.addUserAccount(userAccount);
    }

    /**
     * e签宝平台上创建个人签署账号
     *
     * @param token       token
     * @param userAccount 个人签署账号
     * @return e签宝平台上个人签署账号ID
     */
    @Override
    public String addUserAccountToSignPlatform(String token, UserAccount userAccount) {
        String url = Constant.E_SIGN_BASE_URL + "/v1/accounts/createByThirdPartyUserId";
        String accountId;
        SystemConfig systemConfig = systemConfigMapper.getSystemConfig(SystemConfigTypeEnum.ESIGN.getType());
        if (null == systemConfig) {
            // 未做电子合同相关配置
            LOG.error("[addUserAccountToSignPlatform], " + ResponseMsg.ESIGN_CONFIG_EMPTY.getResponseMessage());
            return Constant.EMPTY_STRING;
        }
        ContractApiConfig contractApiConfig = JSONObject.parseObject(systemConfig.getValue(), ContractApiConfig.class);

        HttpHead httpHead = new HttpHead();
        httpHead.setHeader("X-Tsign-Open-App-Id", contractApiConfig.getAppId());
        httpHead.setHeader("X-Tsign-Open-Token", token);
        httpHead.setHeader("Content-Type", "application/json");
        try {
            String result = HttpUtil.httpPost(url, httpHead.getAllHeaders(), JSON.toJSONString(userAccount));
            ApiResult apiResult = JSONObject.parseObject(result, ApiResult.class);
            LOG.info("[addUserAccountToSignPlatform], apiResult: " + apiResult);
            if (apiResult.isSuccess()) {
                // 成功
                accountId = apiResult.getData().get("accountId").toString();
                LOG.info("[addUserAccountToSignPlatform], accountId: " + accountId);
            } else {
                // 失败
                LOG.error("[addUserAccountToSignPlatform], " + apiResult.getMessage());
                accountId = Constant.EMPTY_STRING;
            }
        } catch (Exception e) {
            e.printStackTrace();
            accountId = Constant.EMPTY_STRING;
        }
        return accountId;
    }

    /**
     * e签宝平台上注销个人签署账号
     *
     * @param token     token
     * @param accountId 个人签署账号ID
     * @return true: 注销成功   false: 注销失败
     */
    @Override
    public boolean deleteUserAccountFromSignPlatform(String token, String accountId) {
        boolean flag;
        String url = Constant.E_SIGN_BASE_URL + "/v1/accounts/" + accountId;
        SystemConfig systemConfig = systemConfigMapper.getSystemConfig(SystemConfigTypeEnum.ESIGN.getType());
        if (null == systemConfig) {
            // 未做电子合同相关配置
            LOG.error("[deleteUserAccountFromSignPlatform], " + ResponseMsg.ESIGN_CONFIG_EMPTY.getResponseMessage());
        }
        ContractApiConfig contractApiConfig = JSONObject.parseObject(systemConfig.getValue(), ContractApiConfig.class);

        HttpHead httpHead = new HttpHead();
        httpHead.setHeader("X-Tsign-Open-App-Id", contractApiConfig.getAppId());
        httpHead.setHeader("X-Tsign-Open-Token", token);
        httpHead.setHeader("Content-Type", "application/json");
        try {
            String result = HttpUtil.httpDelete(url, httpHead.getAllHeaders());
            ApiResult apiResult = JSONObject.parseObject(result, ApiResult.class);
            if (apiResult.isSuccess()) {
                // 成功
                flag = true;
            } else {
                // 失败
                LOG.error("[deleteUserAccountFromSignPlatform], " + apiResult.getMessage());
                flag = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

}
