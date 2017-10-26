package com.xindi.platform.core.shiro.realm;

import com.xindi.platform.core.shiro.authenInfo.OauthAuthenticationInfo;
import com.xindi.platform.core.shiro.config.OauthLoginType;
import com.xindi.platform.core.shiro.token.OauthToken;
import com.xindi.platform.core.shiro.user.ShiroUser;
import com.xindi.platform.core.shiro.user.ShiroUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class OauthAuthorizingRealm extends AuthorizingRealm {

    private final ShiroUserService userService;

    @Autowired
    public OauthAuthorizingRealm(ShiroUserService userService) {//TODO 测试 用户信息相关内容
        if (userService == null) {
            throw new ShiroException("未找到类型为[com.xindi.platform.core.shiro.user.ShiroUserService]的对象");
        }
        this.userService = userService;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) {
            throw new AuthorizationException("Principal对象不能为空");
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 获取当前Realm的授权用户信息
        for (Object o : principals.fromRealm(getName())) {
            ShiroUser userModel = (ShiroUser) o;
            //perms
            //添加用户所具备的权限
            info.addRoles(userModel.getRoles());
            info.addStringPermissions(userModel.getPerms());
        }
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        OauthToken oauthToken = (OauthToken) token;
        String thirdPlatformUserId;

        if (oauthToken.getLoginType() == OauthLoginType.AUTH_CODE) {
            thirdPlatformUserId = oauthToken.getPlatform().getThirdPlatformUserId(oauthToken.getLoginCode());
        } else {
            thirdPlatformUserId = oauthToken.getLoginCode();
        }

        if (StringUtils.isBlank(thirdPlatformUserId)) {
            throw new AccountException("获取第三方用户id异常");
        }

        ShiroUser userInfo = userService.getUserInfoByPlatformInfo(oauthToken.getPlatform().getThirdPlatformCode(), thirdPlatformUserId);

        if (userInfo == null) {
            throw new UnknownAccountException("第三方用户信息未绑定");
        }
        return new OauthAuthenticationInfo(userInfo, getName());
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return OauthToken.class.isInstance(token);
    }
}
