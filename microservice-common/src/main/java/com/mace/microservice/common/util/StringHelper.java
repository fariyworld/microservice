package com.mace.microservice.common.util;

import java.util.UUID;

/**
 * description: 字符串处理工具类.
 * <br />
 * Created by mace on 22:54 2018/6/9.
 */
public final class StringHelper {

    /**
     * description: 构造方法
     * <br /><br />
     * create by mace on 2018/6/9 22:55.
     */
    private StringHelper() { }


    /**
     * description: 随机生成 32位 唯一的不重复的 uuid 去掉 -
     *
     * 9483b9f504bb4c66aa1e48c0c44f8d27
     * <br /><br />
     * create by mace on 2018/6/11 9:15.
     * @param
     * @return: java.lang.String
     */
    public static String getUUIDString(){

        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
