package com.bc.contract.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bc.common.cons.Constant;
import com.bc.common.cons.RedisKey;
import com.bc.common.enums.ResponseMsg;
import com.bc.common.redis.RedisDao;
import com.bc.common.utils.HttpUtil;
import com.bc.contract.entity.ApiResult;
import com.bc.contract.entity.ContractApiConfig;
import com.bc.contract.entity.SystemConfig;
import com.bc.contract.enums.SystemConfigTypeEnum;
import com.bc.contract.mapper.SystemConfigMapper;
import com.bc.contract.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * token
 *
 * @author zhou
 */
@Service("tokenService")
public class TokenServiceImpl implements TokenService {

    private static final Logger LOG = LoggerFactory.getLogger(TokenServiceImpl.class);

    @Resource
    SystemConfigMapper systemConfigMapper;

    @Resource
    RedisDao redisDao;

    /**
     * 获取token
     *
     * @return token
     */
    @Override
    public String getToken() {
        String token = getTokenByCache();
        if (StringUtils.isEmpty(token)) {
            token = getTokenByApi();
            redisDao.set(RedisKey.E_SIGN_TOKEN, token);
        }
        return token;
    }

    /**
     * 通过缓存获取token
     *
     * @return token
     */
    private String getTokenByCache() {
        return (String) redisDao.get(RedisKey.E_SIGN_TOKEN);
    }

    /**
     * 通过API获取token
     *
     * @return token
     */
    private String getTokenByApi() {
        String token;
        SystemConfig systemConfig = systemConfigMapper.getSystemConfig(SystemConfigTypeEnum.ESIGN.getType());
        if (null == systemConfig) {
            // 未做电子合同相关配置
            LOG.error("[getToken], " + ResponseMsg.ESIGN_CONFIG_EMPTY.getResponseMessage());
            return Constant.EMPTY_STRING;
        }
        ContractApiConfig contractApiConfig = JSONObject.parseObject(systemConfig.getValue(), ContractApiConfig.class);

        String url = "https://openapi.esign.cn/v1/oauth2/access_token?appId="
                + contractApiConfig.getAppId() + "&secret=" + contractApiConfig.getSecret() + "&grantType=" + contractApiConfig.getGrantType();
        try {
            String result = HttpUtil.httpGet(url);
            ApiResult apiResult = JSONObject.parseObject(result, ApiResult.class);
            token = apiResult.getData().get("token").toString();
        } catch (Exception e) {
            e.printStackTrace();
            token = Constant.EMPTY_STRING;
        }
        return token;
    }

}
