package com.bc.contract.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bc.common.utils.HttpUtil;
import com.bc.contract.entity.ApiResult;
import com.bc.contract.entity.ContractApiConfig;
import com.bc.contract.entity.SystemConfig;
import com.bc.contract.enums.SystemConfigTypeEnum;
import com.bc.contract.mapper.SystemConfigMapper;
import com.bc.contract.service.TokenService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * token
 *
 * @author zhou
 */
@Service("tokenService")
public class TokenServiceImpl implements TokenService {

    @Resource
    SystemConfigMapper systemConfigMapper;

    /**
     * 获取token
     *
     * @return token
     */
    @Override
    public String getToken() {
        String token;
        SystemConfig systemConfig = systemConfigMapper.getSystemConfig(SystemConfigTypeEnum.ESIGN.getType());
        ContractApiConfig contractApiConfig = JSONObject.parseObject(systemConfig.getValue(), ContractApiConfig.class);

        String url = "https://openapi.esign.cn/v1/oauth2/access_token?appId="
                + contractApiConfig.getAppId() + "&secret=" + contractApiConfig.getSecret() + "&grantType=" + contractApiConfig.getGrantType();
        try {
            String result = HttpUtil.httpGet(url);
            ApiResult apiResult = JSONObject.parseObject(result, ApiResult.class);
            token = apiResult.getData().get("token").toString();
        } catch (Exception e) {
            e.printStackTrace();
            token = "";
        }
        return token;
    }

}
