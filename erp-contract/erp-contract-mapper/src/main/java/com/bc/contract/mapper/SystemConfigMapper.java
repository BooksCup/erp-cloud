package com.bc.contract.mapper;

import com.bc.contract.entity.SystemConfig;

/**
 * 系统配置
 *
 * @author zhou
 */
public interface SystemConfigMapper {

    /**
     * 根据类型获取系统配置
     *
     * @param key 类型
     * @return 系统配置
     */
    SystemConfig getSystemConfig(String key);

}
