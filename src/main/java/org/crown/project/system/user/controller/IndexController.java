package org.crown.project.system.user.controller;

import java.time.LocalDate;
import java.util.List;

import org.crown.framework.springboot.properties.CrownProperties;
import org.crown.framework.web.controller.WebController;
import org.crown.project.system.menu.domain.Menu;
import org.crown.project.system.menu.service.IMenuService;
import org.crown.project.system.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Home Business Processing
 *
 * @author Crown
 */
@Controller
public class IndexController extends WebController {

    @Autowired
    private IMenuService menuService;

    @Autowired
    private CrownProperties crownConfig;

    // System Home
    @GetMapping("/index")
    public String index(ModelMap mmap) {
        // Get identity information
        User user = getSysUser();
        // Take out the menu based on user id
        List<Menu> menus = menuService.selectMenusByUser(user);
        mmap.put("menus", menus);
        mmap.put("user", user);
        mmap.put("copyRightYear", LocalDate.now().getYear());
        mmap.put("demoEnabled", crownConfig.getDemo().isEnabled());
        return "index";
    }

    // system introduction
    @GetMapping("/system/main")
    public String main() {
        return "main";
    }
}
