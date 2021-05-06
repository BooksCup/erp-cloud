package com.bc.common.utils;

import java.util.UUID;

/**
 * 主键工具类
 *
 * @author zhou
 */
public class PrimaryKeyUtil {

    /**
     * 生成主键
     *
     * @return 主键
     */
    public static String generateId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
