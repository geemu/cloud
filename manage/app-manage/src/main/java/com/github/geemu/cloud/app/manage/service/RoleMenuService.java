package com.github.geemu.cloud.app.manage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.geemu.cloud.mapper.manage.RoleMenuMapper;
import com.github.geemu.cloud.model.manage.entity.MenuEntity;
import com.github.geemu.cloud.model.manage.entity.RoleEntity;
import com.github.geemu.cloud.model.manage.entity.RoleMenuEntity;
import com.github.geemu.cloud.model.manage.entity.view.MenuRoleView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色资源关联操作
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-03-08 21:04
 */
@Slf4j
@Service
public class RoleMenuService {

    @Autowired private MenuService menuService;
    @Autowired private RoleService roleService;
    @Autowired private RoleMenuMapper roleMenuMapper;

    /**
     * 根据角色id列表,查询有效关联资源id列表
     * @param roleIds 角色id列表
     * @return 有效的关联资源id列表
     */
    public List<Long> findByRoleIds(List<Long> roleIds) {
        RoleMenuEntity entity = RoleMenuEntity
                .builder()
                .enabled(Boolean.TRUE)
                .build();
        QueryWrapper<RoleMenuEntity> query = new QueryWrapper<>();
        query.setEntity(entity);
        query.in("role_id", roleIds);
        List<RoleMenuEntity> list = roleMenuMapper.selectList(query);
        return list.stream()
                .map(RoleMenuEntity::getMenuId)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * 查询有效菜单列表,及其可以访问的有效角色列表
     * @return 有效菜单列表, 及其可以访问的有效角色列表
     */
    public List<MenuRoleView> findAllWithRole() {
        // 所有有效菜单列表
        List<MenuEntity> allMenuList = menuService.findAll();
        // 所有有效关联角色列表
        List<RoleMenuEntity> allUnionRoleList = findByMenuIds(allMenuList.stream().map(MenuEntity::getMenuId).collect(Collectors.toCollection(ArrayList::new)));
        // 所有有效角色列表
        List<RoleEntity> allRoleList = roleService.findByRoleIds(allUnionRoleList.stream().map(RoleMenuEntity::getRoleId).collect(Collectors.toCollection(ArrayList::new)));
        return allMenuList.stream()
                .map(menuEntity -> {
                    // 有效菜单
                    MenuRoleView item = MenuRoleView
                            .builder()
                            .menuId(menuEntity.getMenuId())
                            .parentId(menuEntity.getParentId())
                            .name(menuEntity.getName())
                            .type(menuEntity.getType())
                            .method(menuEntity.getMethod())
                            .pattern(menuEntity.getPattern())
                            .operate(menuEntity.getOperate())
                            .sort(menuEntity.getSort())
                            .remark(menuEntity.getRemark())
                            .enabled(menuEntity.getEnabled())
                            .createUser(menuEntity.getCreateUser())
                            .createTime(menuEntity.getCreateTime())
                            .updateUser(menuEntity.getUpdateUser())
                            .updateTime(menuEntity.getUpdateTime())
                            .build();
                    // 有效关联角色id列表
                    List<Long> unionRoleIdList = allUnionRoleList.stream()
                            .filter(roleMenuEntity -> menuEntity.getMenuId().equals(roleMenuEntity.getMenuId()))
                            .map(RoleMenuEntity::getRoleId)
                            .collect(Collectors.toCollection(ArrayList::new));
                    // 有效角色列表
                    List<RoleEntity> roleList = allRoleList.stream()
                            .filter(role -> unionRoleIdList.contains(role.getRoleId()))
                            .collect(Collectors.toCollection(ArrayList::new));
                    item.setRoleList(roleList);
                    return item;
                })
                .sorted((o1, o2) -> (int) (o1.getSort() - o2.getSort()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * 根据资源id列表,查询有效关联角色列表
     * @param menuIds 资源id列表
     * @return 有效关联角色列表
     */
    public List<RoleMenuEntity> findByMenuIds(List<Long> menuIds) {
        QueryWrapper<RoleMenuEntity> query = new QueryWrapper<>();
        RoleMenuEntity entity = RoleMenuEntity
                .builder()
                .enabled(Boolean.TRUE)
                .build();
        query.setEntity(entity);
        query.in("menu_id", menuIds);
        return roleMenuMapper.selectList(query);
    }

}
