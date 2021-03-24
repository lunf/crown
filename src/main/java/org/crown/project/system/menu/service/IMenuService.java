package org.crown.project.system.menu.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.crown.framework.service.BaseService;
import org.crown.framework.web.domain.Ztree;
import org.crown.project.system.menu.domain.Menu;
import org.crown.project.system.role.domain.Role;
import org.crown.project.system.user.domain.User;

/**
 * Menu business layer
 *
 * @author Crown
 */
public interface IMenuService extends BaseService<Menu> {

    /**
     * Query menu based on user ID
     *
     * @param user User Info
     * @return Menu list
     */
    List<Menu> selectMenusByUser(User user);

    /**
     * Query system menu list
     *
     * @param menu Menu information
     * @return Menu list
     */
    List<Menu> selectMenuList(Menu menu);

    /**
     * Query permissions based on user ID
     *
     * @param userId User ID
     * @return Permission list
     */
    Set<String> selectPermsByUserId(Long userId);

    /**
     * Query menu based on role ID
     *
     * @param role Role object
     * @return Menu list
     */
    List<Ztree> roleMenuTreeData(Role role);

    /**
     * Query all menu information
     *
     * @return Menu list
     */
    List<Ztree> menuTreeData();

    /**
     * Query all permissions of the system
     *
     * @return Permission list
     */
    Map<String, String> selectPermsAll();

    /**
     * Delete menu management information
     *
     * @param menuId Menu ID
     * @return result
     */
    boolean deleteMenuById(Long menuId);

    /**
     * Query information based on menu ID
     *
     * @param menuId Menu ID
     * @return Menu information
     */
    Menu selectMenuById(Long menuId);

    /**
     * Added save menu information
     *
     * @param menu Menu information
     * @return result
     */
    boolean insertMenu(Menu menu);

    /**
     * Modify and save menu information
     *
     * @param menu Menu information
     * @return result
     */
    boolean updateMenu(Menu menu);

    /**
     * Verify that the menu name is unique
     *
     * @param menu Menu information
     * @return result
     */
    boolean checkMenuNameUnique(Menu menu);
}
