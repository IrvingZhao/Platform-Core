package com.xindi.platform.core.shiro.matcher;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

/**
 * oauth 登陆信息检查
 * <p>因为oauth 用户信息获取的位置存放在 {@link com.xindi.platform.core.shiro.realm.OauthAuthorizingRealm}中，只要token和info均不为null，则通过</p>
 */
public class OauthCredentialsMatcher implements CredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        return token != null && info != null;
    }
}
