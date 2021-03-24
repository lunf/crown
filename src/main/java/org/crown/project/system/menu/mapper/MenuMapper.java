package org.crown.project.system.menu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.crown.framework.mapper.BaseMapper;
import org.crown.project.system.menu.domain.Menu;

/**
 * Menu table Data layer
 *
 * @author Crown
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * Query the normal display menu of the system (without buttons)
     *
     * @return Menu list
     */
    List<Menu> selectMenuNormalAll();

    /**
     * Query menu based on user ID
     *
     * @param userId User ID
     * @return Menu list
     */
    List<Menu> selectMenuAllByUserId(Long userId);

    /**
     * Query system menu list
     *
     * @param menu Menu information
     * @return Menu list
     */
    List<Menu> selectMenuListByUserId(Menu menu);

    /**
     * Query menu based on user ID
     *
     * @param userId User ID
     * @return Menu list
     */
    List<Menu> selectMenusByUserId(Long userId);

    /**
     * Query permissions based on user ID
     *
     * @param userId User ID
     * @return Permission list
     */
    List<String> selectPermsByUserId(Long userId);

    /**
     * Query menu based on role ID
     *
     * @param roleId Role ID
     * @return Menu list
     */
    List<String> selectMenuTree(Long roleId);

    /**
     * Query information based on menu ID
     *
     * @param menuId Menu ID
     * @return Menu information
     */
    Menu selectMenuById(Long menuId);

}
