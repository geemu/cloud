package com.github.geemu.cloud.app.manage.config.security;

import com.github.geemu.cloud.app.manage.service.RoleMenuService;
import com.github.geemu.cloud.app.manage.service.RoleService;
import com.github.geemu.cloud.model.manage.entity.RoleEntity;
import com.github.geemu.cloud.model.manage.entity.view.MenuRoleView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 从数据库获取资源
 * @author 陈方明  cfmmail@sina.com
 * @since 2018-11-23 17:44
 */
@Data
@Slf4j
@Builder
@Component
@AllArgsConstructor
public class MetadataSource implements FilterInvocationSecurityMetadataSource {

    /** 路由匹配 **/
    private static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();
    /** 角色资源关联操作 **/
    private RoleMenuService roleMenuService;
    /** 角色操作 **/
    private RoleService roleService;

    /**
     * 能够访问当前资源的角色列表
     * 返回给 decide 方法 {@link AccessDecideManager}
     * @param object 被保护的对象
     * @return 能够访问当前资源的角色列表
     * @throws IllegalArgumentException 参数异常
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        HttpServletRequest request = ((FilterInvocation) object).getRequest();
        String path = request.getMethod() + ":" + request.getRequestURI();
        log.info("当前path->{}", path);
        // 可以访问当前请求资源的角色列表
        List<String> col = new ArrayList<>();
        boolean hasMatched = Boolean.FALSE;
        // 所有有效菜单及其可以访问的有效角色
        List<MenuRoleView> menuWithRoleList = roleMenuService.findAllWithRole();
        for (MenuRoleView item : menuWithRoleList) {
            List<RoleEntity> roleList = item.getRoleList();
            String fullPattern = item.getMethod() + ":" + item.getPattern();
            boolean match = ANT_PATH_MATCHER.match(fullPattern, path);
            if (match) {
                hasMatched = Boolean.TRUE;
                log.info("pattern->{},match->true", fullPattern);
                col.addAll(roleList.stream().map(e -> e.getRoleId().toString()).collect(Collectors.toCollection(ArrayList::new)));
            }
        }
        // 没有匹配上的资源->即后台资源表未配置相关资源，那么当前资源只要登录就可以访问,即所有角色都可以访问
        if (!hasMatched) {
            col.addAll(roleService.findAll().stream().map(e -> e.getRoleId().toString()).collect(Collectors.toCollection(ArrayList::new)));
            return SecurityConfig.createList(col.toArray(new String[0]));
        }
        // 有匹配上的资源，但资源未配置相关的角色->那么没有任何角色可以访问该资源，赋予一个不存在的角色以防不走{@link AccessDecideManager}
        if (CollectionUtils.isEmpty(col)) {
            // 添加一个不存在的角色
            col.add("-1");
            return SecurityConfig.createList(col.toArray(new String[0]));
        }
        return SecurityConfig.createList(col.toArray(new String[0]));
    }

    /**
     * 如果可用,则返回实现类定义的所有{@code ConfigAttribute}.
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    /**
     * 指示{@code Security Meta dataSource}实现.
     * 是否能够为指示的安全对象类型提供{@code ConfigAttribute}
     * @param clazz 正在查询的类
     * @return true 如果实现可以处理指示的类
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

}
