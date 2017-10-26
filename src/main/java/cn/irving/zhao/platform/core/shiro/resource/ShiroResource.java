package cn.irving.zhao.platform.core.shiro.resource;

import java.util.Map;

public interface ShiroResource {

    /**
     * 请求地址
     */
    String getPath();

    /**
     * 获取资源所需权限
     */
    Map<String, String> getPerms();

}
