# Shiro工具包
##提供功能

1. oauth登陆相关类
2. 用户名密码登陆相关类
3. 自定义登陆重定向地址
4. 单资源多过滤器间使用 或 关系

## 使用方法

### 第一步：声明CredentialsMatcher
> 用户名密码登陆

    <bean id="passwordMatcher"  class="com.demo.web.CustomCredentialsMatcher" />

> oauth 登陆

    
