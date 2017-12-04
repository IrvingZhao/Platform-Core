# Shiro工具包
##提供功能

1. oauth登陆相关类
2. 用户名密码登陆相关类
3. 自定义登陆重定向地址
4. 单资源多过滤器间使用 或 关系
5. 自定义资源加载器

## 使用方法

### 第一步：声明CredentialsMatcher
> 用户名密码登陆
>
> ```xml
> <bean id="passwordMatcher"  class="cn.irving.zhao.platform.core.shiro.matcher.PasswordCredentialsMatcher" />
> ```
>
> oauth登陆
>
> ```xml
> <bean id="oauthMatcher"  class="cn.irving.zhao.platform.core.shiro.matcher.OauthCredentialsMatcher" />
> ```

### 第二部：声明realms

> 用户名密码登陆，匹配`cn.irving.zhao.platform.core.shiro.token.PasswordToken`
>
> ```xml
> <bean id="passwordRealm" class="cn.irving.zhao.platform.core.shiro.realm.PasswordAuthorizingRealm">
>   <property name="credentialsMatcher" ref="passwordMatcher" />
> </bean>
> ```
>
> oauth 登陆，匹配 `cn.irving.zhao.platform.core.shiro.token.OauthToken`
>
> ```xml
> <bean id="oauthRealm" class="cn.irving.zhao.platform.core.shiro.realm.OauthAuthorizingRealm">
>   <property name="credentialsMatcher" ref="oauthMatcher" />
> </bean>
> ```
>
> 在使用realm时，需要向spring中注入一个` cn.irving.zhao.platform.core.shiro.user.ShiroUserService` 用于获取用户相关信息，包括 `getUserInfoByUserName` 根据用户名获取用户，`getUserInfoByPlatformInfo` 根据第三方平台code及第三方平台id获取用户，在使用的时候，可选择性覆盖相关方法。
>
> realm中的`credentialsMatcher` 属性必须时相匹配的，否则会导致登陆出现问题

### 第三步：声明SecurityManager

> 声明securityManager
>
> ```xml
> <bean id="securityManager" class="org.apache.shiro.web.mgt.DefaultWebSecurityManager">
>   <property name="realm" ref="oauthRealm" /><!-- 使用单一realm -->
>   <property name="realms"> <!-- 使用多realm -->
>     <list>
>       <ref bean="oauthRealm"/>
>       <ref bean="passwordRealm"/>
>     </list>
>   </property>
> </bean>
> ```

### 第四步：声明资源加载工厂ResourceFactory

> `cn.irving.zhao.platform.core.shiro.resources.ShiroResourceService` 用于获取资源列表，在使用`cn.irving.zhao.platform.core.shiro.resource` 时，需预先创建相关资源工厂

### 第五步：声明ShiroFilterFactory并指定SecurityManager

> 当使用 功能3或功能4时，ShiroFilterFactory 需 使用 `cn.irving.zhao.platform.core.shiro.CustomShiroFilterFactoryBean`

