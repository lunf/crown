package org.crown.project.demo.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.crown.common.utils.StringUtils;
import org.crown.common.utils.converter.Convert;
import org.crown.common.utils.poi.ExcelUtils;
import org.crown.framework.exception.Crown2Exception;
import org.crown.framework.model.ExcelDTO;
import org.crown.framework.responses.ApiResponses;
import org.crown.framework.web.controller.WebController;
import org.crown.framework.web.page.PageDomain;
import org.crown.framework.web.page.TableData;
import org.crown.project.demo.domain.UserOperateModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;

/**
 * Operational control
 *
 * @author Crown
 */
@Controller
@RequestMapping("/demo/operate")
public class DemoOperateController extends WebController {

    private final String prefix = "demo/operate";

    private final static Map<Integer, UserOperateModel> users = new LinkedHashMap<>();

    static {
        users.put(1, new UserOperateModel(1, "1000001", "2222222", "0", "15888888888", "crown@qq.com", 150.0, "0"));
        users.put(2, new UserOperateModel(2, "1000002", "测试2", "1", "15666666666", "crown@qq.com", 180.0, "1"));
        users.put(3, new UserOperateModel(3, "1000003", "测试3", "0", "15666666666", "crown@qq.com", 110.0, "1"));
        users.put(4, new UserOperateModel(4, "1000004", "测试4", "1", "15666666666", "crown@qq.com", 220.0, "1"));
        users.put(5, new UserOperateModel(5, "1000005", "测试5", "0", "15666666666", "crown@qq.com", 140.0, "1"));
        users.put(6, new UserOperateModel(6, "1000006", "测试6", "1", "15666666666", "crown@qq.com", 330.0, "1"));
        users.put(7, new UserOperateModel(7, "1000007", "测试7", "0", "15666666666", "crown@qq.com", 160.0, "1"));
        users.put(8, new UserOperateModel(8, "1000008", "测试8", "1", "15666666666", "crown@qq.com", 170.0, "1"));
        users.put(9, new UserOperateModel(9, "1000009", "测试9", "0", "15666666666", "crown@qq.com", 180.0, "1"));
        users.put(10, new UserOperateModel(10, "1000010", "测试10", "0", "15666666666", "crown@qq.com", 210.0, "1"));
        users.put(11, new UserOperateModel(11, "1000011", "测试11", "1", "15666666666", "crown@qq.com", 110.0, "1"));
        users.put(12, new UserOperateModel(12, "1000012", "测试12", "0", "15666666666", "crown@qq.com", 120.0, "1"));
        users.put(13, new UserOperateModel(13, "1000013", "测试13", "1", "15666666666", "crown@qq.com", 380.0, "1"));
        users.put(14, new UserOperateModel(14, "1000014", "测试14", "0", "15666666666", "crown@qq.com", 280.0, "1"));
        users.put(15, new UserOperateModel(15, "1000015", "测试15", "0", "15666666666", "crown@qq.com", 570.0, "1"));
        users.put(16, new UserOperateModel(16, "1000016", "测试16", "1", "15666666666", "crown@qq.com", 260.0, "1"));
        users.put(17, new UserOperateModel(17, "1000017", "测试17", "1", "15666666666", "crown@qq.com", 210.0, "1"));
        users.put(18, new UserOperateModel(18, "1000018", "测试18", "1", "15666666666", "crown@qq.com", 340.0, "1"));
        users.put(19, new UserOperateModel(19, "1000019", "测试19", "1", "15666666666", "crown@qq.com", 160.0, "1"));
        users.put(20, new UserOperateModel(20, "1000020", "测试20", "1", "15666666666", "crown@qq.com", 220.0, "1"));
        users.put(21, new UserOperateModel(21, "1000021", "测试21", "1", "15666666666", "crown@qq.com", 120.0, "1"));
        users.put(22, new UserOperateModel(22, "1000022", "测试22", "1", "15666666666", "crown@qq.com", 130.0, "1"));
        users.put(23, new UserOperateModel(23, "1000023", "测试23", "1", "15666666666", "crown@qq.com", 490.0, "1"));
        users.put(24, new UserOperateModel(24, "1000024", "测试24", "1", "15666666666", "crown@qq.com", 570.0, "1"));
        users.put(25, new UserOperateModel(25, "1000025", "测试25", "1", "15666666666", "crown@qq.com", 250.0, "1"));
        users.put(26, new UserOperateModel(26, "1000026", "测试26", "1", "15666666666", "crown@qq.com", 250.0, "1"));
    }

    /**
     * table
     */
    @GetMapping("/table")
    public String table() {
        return prefix + "/table";
    }

    /**
     * other
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
    public TableData<UserOperateModel> list(UserOperateModel userModel) {
        TableData rspData = new TableData();
        List<UserOperateModel> userList = new ArrayList<>(users.values());
        // Query filter
        if (StringUtils.isNotEmpty(userModel.getSearchValue())) {
            userList.clear();
            for (Map.Entry<Integer, UserOperateModel> entry : users.entrySet()) {
                if (entry.getValue().getUserName().equals(userModel.getSearchValue())) {
                    userList.add(entry.getValue());
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

    /**
     * New users
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * Add saved user
     */
    @PostMapping("/add")
    @ResponseBody
    public void addSave(UserOperateModel user) {
        int userId = users.size() + 1;
        user.setUserId(userId);
        users.put(userId, user);
    }

    /**
     * Modify user
     */
    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") Integer userId, ModelMap mmap) {
        mmap.put("user", users.get(userId));
        return prefix + "/edit";
    }

    /**
     * Modify saved user
     */
    @PostMapping("/edit")
    @ResponseBody
    public void editSave(UserOperateModel user) {
        users.put(user.getUserId(), user);
    }

    /**
     * Export
     */
    @PostMapping("/export")
    @ResponseBody
    public ExcelDTO export(UserOperateModel user) {
        List<UserOperateModel> list = new ArrayList<>(users.values());
        ExcelUtils<UserOperateModel> util = new ExcelUtils<>(UserOperateModel.class);
        return new ExcelDTO(util.exportExcel(list, "用户数据"));
    }

    /**
     * Download template
     */
    @GetMapping("/importTemplate")
    @ResponseBody
    public ExcelDTO importTemplate() {
        ExcelUtils<UserOperateModel> util = new ExcelUtils<>(UserOperateModel.class);
        return new ExcelDTO(util.importTemplateExcel("用户数据"));
    }

    /**
     * Import Data
     */
    @PostMapping("/importData")
    @ResponseBody
    public ApiResponses<Void> importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtils<UserOperateModel> util = new ExcelUtils<>(UserOperateModel.class);
        List<UserOperateModel> userList = util.importExcel(file.getInputStream());
        String message = importUser(userList, updateSupport);
        return success().setMsg(message);
    }

    /**
     * delete users
     */
    @PostMapping("/remove")
    @ResponseBody
    public void remove(String ids) {
        Integer[] userIds = Convert.toIntArray(ids);
        for (Integer userId : userIds) {
            users.remove(userId);
        }
    }

    /**
     * View details
     */
    @GetMapping("/detail/{userId}")
    public String detail(@PathVariable("userId") Integer userId, ModelMap mmap) {
        mmap.put("user", users.get(userId));
        return prefix + "/detail";
    }

    @PostMapping("/clean")
    @ResponseBody
    public void clean() {
        users.clear();
    }

    /**
     * Import user data
     *
     * @param userList        User data list
     * @param isUpdateSupport Whether to update support, if it already exists, update the data
     * @return result
     */
    public String importUser(List<UserOperateModel> userList, Boolean isUpdateSupport) {
        if (CollectionUtils.isNotEmpty(userList)) {
            throw new Crown2Exception(HttpServletResponse.SC_BAD_REQUEST, "导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (UserOperateModel user : userList) {
            try {
                // Verify if this user exists
                boolean userFlag = false;
                for (Map.Entry<Integer, UserOperateModel> entry : users.entrySet()) {
                    if (entry.getValue().getUserName().equals(user.getUserName())) {
                        userFlag = true;
                        break;
                    }
                }
                if (!userFlag) {
                    int userId = users.size() + 1;
                    user.setUserId(userId);
                    users.put(userId, user);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、user ").append(user.getUserName()).append(" Imported successfully");
                } else if (isUpdateSupport) {
                    users.put(user.getUserId(), user);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、user ").append(user.getUserName()).append(" update completed");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("、user ").append(user.getUserName()).append(" already exists");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、account number " + user.getUserName() + "Import failed：";
                failureMsg.append(msg).append(e.getMessage());
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "Sorry, the import failed! Total" + failureNum + " The data format is incorrect, the error is as follows：");
            throw new Crown2Exception(HttpServletResponse.SC_BAD_REQUEST, failureMsg.toString());
        } else {
            successMsg.insert(0, "Congratulations, all the data has been imported successfully! Total" + successNum + " Article, the data is as follows：");
        }
        return successMsg.toString();
    }
}
