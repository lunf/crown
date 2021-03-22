package org.crown.project.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.crown.common.utils.StringUtils;
import org.crown.framework.web.controller.WebController;
import org.crown.framework.web.page.PageDomain;
import org.crown.framework.web.page.TableData;
import org.crown.project.demo.domain.UserTableModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Form related
 *
 * @author Crown
 */
@Controller
@RequestMapping("/demo/table")
public class DemoTableController extends WebController {

    private final String prefix = "demo/table";

    private final static List<UserTableModel> users = new ArrayList<>();

    static {
        users.add(new UserTableModel(1, "1000001", "test1", "0", "15888888888", "crown@qq.com", 150.0, "0"));
        users.add(new UserTableModel(2, "1000002", "test2", "1", "15666666666", "crown@qq.com", 180.0, "1"));
        users.add(new UserTableModel(3, "1000003", "test3", "0", "15666666666", "crown@qq.com", 110.0, "1"));
        users.add(new UserTableModel(4, "1000004", "test4", "1", "15666666666", "crown@qq.com", 220.0, "1"));
        users.add(new UserTableModel(5, "1000005", "test5", "0", "15666666666", "crown@qq.com", 140.0, "1"));
        users.add(new UserTableModel(6, "1000006", "test6", "1", "15666666666", "crown@qq.com", 330.0, "1"));
        users.add(new UserTableModel(7, "1000007", "test7", "0", "15666666666", "crown@qq.com", 160.0, "1"));
        users.add(new UserTableModel(8, "1000008", "test8", "1", "15666666666", "crown@qq.com", 170.0, "1"));
        users.add(new UserTableModel(9, "1000009", "test9", "0", "15666666666", "crown@qq.com", 180.0, "1"));
        users.add(new UserTableModel(10, "1000010", "test10", "0", "15666666666", "crown@qq.com", 210.0, "1"));
        users.add(new UserTableModel(11, "1000011", "test11", "1", "15666666666", "crown@qq.com", 110.0, "1"));
        users.add(new UserTableModel(12, "1000012", "test12", "0", "15666666666", "crown@qq.com", 120.0, "1"));
        users.add(new UserTableModel(13, "1000013", "test13", "1", "15666666666", "crown@qq.com", 380.0, "1"));
        users.add(new UserTableModel(14, "1000014", "test14", "0", "15666666666", "crown@qq.com", 280.0, "1"));
        users.add(new UserTableModel(15, "1000015", "test15", "0", "15666666666", "crown@qq.com", 570.0, "1"));
        users.add(new UserTableModel(16, "1000016", "test16", "1", "15666666666", "crown@qq.com", 260.0, "1"));
        users.add(new UserTableModel(17, "1000017", "test17", "1", "15666666666", "crown@qq.com", 210.0, "1"));
        users.add(new UserTableModel(18, "1000018", "test18", "1", "15666666666", "crown@qq.com", 340.0, "1"));
        users.add(new UserTableModel(19, "1000019", "test19", "1", "15666666666", "crown@qq.com", 160.0, "1"));
        users.add(new UserTableModel(20, "1000020", "test20", "1", "15666666666", "crown@qq.com", 220.0, "1"));
        users.add(new UserTableModel(21, "1000021", "test21", "1", "15666666666", "crown@qq.com", 120.0, "1"));
        users.add(new UserTableModel(22, "1000022", "test22", "1", "15666666666", "crown@qq.com", 130.0, "1"));
        users.add(new UserTableModel(23, "1000023", "test23", "1", "15666666666", "crown@qq.com", 490.0, "1"));
        users.add(new UserTableModel(24, "1000024", "test24", "1", "15666666666", "crown@qq.com", 570.0, "1"));
        users.add(new UserTableModel(25, "1000025", "test25", "1", "15666666666", "crown@qq.com", 250.0, "1"));
        users.add(new UserTableModel(26, "1000026", "test26", "1", "15666666666", "crown@qq.com", 250.0, "1"));
    }

    /**
     * Search related
     */
    @GetMapping("/search")
    public String search() {
        return prefix + "/search";
    }

    /**
     * Data summary
     */
    @GetMapping("/footer")
    public String footer() {
        return prefix + "/footer";
    }

    /**
     * Combination header
     */
    @GetMapping("/groupHeader")
    public String groupHeader() {
        return prefix + "/groupHeader";
    }

    /**
     * Form export
     */
    @GetMapping("/export")
    public String export() {
        return prefix + "/export";
    }

    /**
     * Turn the page to remember the choice
     */
    @GetMapping("/remember")
    public String remember() {
        return prefix + "/remember";
    }

    /**
     * Jump to the specified page
     */
    @GetMapping("/pageGo")
    public String pageGo() {
        return prefix + "/pageGo";
    }

    /**
     * Custom query parameters
     */
    @GetMapping("/params")
    public String params() {
        return prefix + "/params";
    }

    /**
     * Multiple tables
     */
    @GetMapping("/multi")
    public String multi() {
        return prefix + "/multi";
    }

    /**
     * Click the button to load the table
     */
    @GetMapping("/button")
    public String button() {
        return prefix + "/button";
    }

    /**
     * Table freeze column
     */
    @GetMapping("/fixedColumns")
    public String fixedColumns() {
        return prefix + "/fixedColumns";
    }

    /**
     * Custom trigger event
     */
    @GetMapping("/event")
    public String event() {
        return prefix + "/event";
    }

    /**
     * Table detail view
     */
    @GetMapping("/detail")
    public String detail() {
        return prefix + "/detail";
    }

    /**
     * Table image preview
     */
    @GetMapping("/image")
    public String image() {
        return prefix + "/image";
    }

    /**
     * Dynamically add, delete, modify and check
     */
    @GetMapping("/curd")
    public String curd() {
        return prefix + "/curd";
    }

    /**
     * Table drag operation
     */
    @GetMapping("/reorder")
    public String reorder() {
        return prefix + "/reorder";
    }

    /**
     * Other operations on the form
     */
    @GetMapping("/other")
    public String other() {
        return prefix + "/other";
    }

    /**
     * Query data
     */
    @PostMapping("/list")
    @ResponseBody
    public TableData<UserTableModel> list(UserTableModel userModel) {
        TableData rspData = new TableData();
        List<UserTableModel> userList = new ArrayList<>(Arrays.asList(new UserTableModel[users.size()]));
        Collections.copy(userList, users);
        // Query filter
        if (StringUtils.isNotEmpty(userModel.getUserName())) {
            userList.clear();
            for (UserTableModel user : users) {
                if (user.getUserName().equals(userModel.getUserName())) {
                    userList.add(user);
                }
            }
        }
        PageDomain pageDomain = getPageDomain();
        if (null == pageDomain.getPageNum() || null == pageDomain.getPageSize()) {
            rspData.setRows(userList);
            rspData.setTotal(userList.size());
            return rspData;
        }
        int pageNum = (pageDomain.getPageNum() - 1) * 10;
        int pageSize = pageDomain.getPageNum() * 10;
        if (pageSize > userList.size()) {
            pageSize = userList.size();
        }
        rspData.setRows(userList.subList(pageNum, pageSize));
        rspData.setTotal(userList.size());
        return rspData;
    }
}

