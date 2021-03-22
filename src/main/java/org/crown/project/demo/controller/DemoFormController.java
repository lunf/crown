package org.crown.project.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.crown.common.utils.Maps;
import org.crown.framework.web.controller.WebController;
import org.crown.project.demo.domain.UserFormModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Form related
 *
 * @author Crown
 */
@Controller
@RequestMapping("/demo/form")
public class DemoFormController extends WebController {

    private final String prefix = "demo/form";

    private final static List<UserFormModel> users = new ArrayList<>();

    static {
        users.add(new UserFormModel(1, "1000001", "测试1", "15888888888"));
        users.add(new UserFormModel(2, "1000002", "测试2", "15666666666"));
        users.add(new UserFormModel(3, "1000003", "测试3", "15666666666"));
        users.add(new UserFormModel(4, "1000004", "测试4", "15666666666"));
        users.add(new UserFormModel(5, "1000005", "测试5", "15666666666"));
    }

    /**
     * Button page
     */
    @GetMapping("/button")
    public String button() {
        return prefix + "/button";
    }

    /**
     * Drop down box
     */
    @GetMapping("/select")
    public String select() {
        return prefix + "/select";
    }

    /**
     * Form validation
     */
    @GetMapping("/validate")
    public String validate() {
        return prefix + "/validate";
    }

    /**
     * Function extension (including file upload)
     */
    @GetMapping("/jasny")
    public String jasny() {
        return prefix + "/jasny";
    }

    /**
     * Drag sort
     */
    @GetMapping("/sortable")
    public String sortable() {
        return prefix + "/sortable";
    }

    /**
     * Tabs & Panels
     */
    @GetMapping("/tabs_panels")
    public String tabs_panels() {
        return prefix + "/tabs_panels";
    }

    /**
     * Grid
     */
    @GetMapping("/grid")
    public String grid() {
        return prefix + "/grid";
    }

    /**
     * Form wizard
     */
    @GetMapping("/wizard")
    public String wizard() {
        return prefix + "/wizard";
    }

    /**
     * File Upload
     */
    @GetMapping("/upload")
    public String upload() {
        return prefix + "/upload";
    }

    /**
     * Date and time page
     */
    @GetMapping("/datetime")
    public String datetime() {
        return prefix + "/datetime";
    }

    /**
     * Mutual selection of components
     */
    @GetMapping("/duallistbox")
    public String duallistbox() {
        return prefix + "/duallistbox";
    }

    /**
     * Basic form
     */
    @GetMapping("/basic")
    public String basic() {
        return prefix + "/basic";
    }

    /**
     * Card list
     */
    @GetMapping("/cards")
    public String cards() {
        return prefix + "/cards";
    }

    /**
     * summernote rich text editor
     */
    @GetMapping("/summernote")
    public String summernote() {
        return prefix + "/summernote";
    }

    /**
     * Search auto-completion
     */
    @GetMapping("/autocomplete")
    public String autocomplete() {
        return prefix + "/autocomplete";
    }

    /**
     * Get user data
     */
    @GetMapping("/userModel")
    @ResponseBody
    public Map<String, Object> userModel() {
        return Maps.<String, Object>builder().put("value", users).build();
    }

    /**
     * Get data collection
     */
    @GetMapping("/collection")
    @ResponseBody
    public Map<String, Object> collection() {
        String[] array = {"Crown 1", "Crown 2", "Crown 3", "Crown 4", "Crown 5"};
        return Maps.<String, Object>builder().put("value", array).build();
    }
}
