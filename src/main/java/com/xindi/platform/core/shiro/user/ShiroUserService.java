package com.xindi.platform.core.shiro.user;

/**
 * 用户信息服务
 */
public interface ShiroUserService {

    /**
     * 根据用户登录名获取用户信息
     *
     * @param username 用户登录名
     * @return 用户信息
     */
    ShiroUser getUserInfoByUserName(String username);

    /**
     * 根据第三方平台信息获取用户信息
     *
     * @param platform       平台编码
     * @param platformUserId 平台用户id
     * @return 用户信息
     */
    ShiroUser getUserInfoByPlatformInfo(String platform, String platformUserId);

}