package org.crown.project.system.menu.domain;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.crown.framework.web.domain.BaseEntity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Getter;
import lombok.Setter;

/**
 * Menu permission table sys_menu
 *
 * @author Crown
 */
@Setter
@Getter
public class Menu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * Menu ID
     */
    @TableId
    private Long menuId;

    /**
     * Menu name
     */
    @NotBlank(message = "Menu name cannot be empty")
    @Size(max = 50, message = "The length of the menu name cannot exceed 50 characters")
    private String menuName;

    /**
     * Parent menu name
     */
    @TableField(exist = false)
    private String parentName;

    /**
     * Parent menu ID
     */
    private Long parentId;

    /**
     * display order
     */
    @NotBlank(message = "Display order cannot be empty")
    private String orderNum;

    /**
     * Menu URL
     */
    @Size(max = 200, message = "The requested address cannot exceed 200 characters")
    private String url;

    /**
     * Open method: menuItem tab menu Blank new window
     */
    private String target;

    /**
     * Type: 0 directory, 1 menu, 2 buttons
     */
    @NotBlank(message = "Menu type cannot be empty")
    private String menuType;

    /**
     * Menu status: 0 display, 1 hide
     */
    private String visible;

    /**
     * Permission string
     */
    @Size(max = 100, message = "The length of the authorization ID cannot exceed 100 characters")
    private String perms;

    /**
     * Menu icon
     */
    private String icon;

    /**
     * Remarks
     */
    private String remark;

    /**
     * Submenu
     */
    @TableField(exist = false)
    private List<Menu> children = new ArrayList<>();

}
