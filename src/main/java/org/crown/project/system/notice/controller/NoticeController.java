package org.crown.project.system.notice.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.crown.common.annotation.Log;
import org.crown.common.enums.BusinessType;
import org.crown.common.utils.StringUtils;
import org.crown.framework.web.controller.WebController;
import org.crown.framework.web.page.TableData;
import org.crown.project.system.notice.domain.Notice;
import org.crown.project.system.notice.service.INoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

/**
 * Announcement Information Operation Processing
 *
 * @author Crown
 */
@Controller
@RequestMapping("/system/notice")
public class NoticeController extends WebController<Notice> {

    private final String prefix = "system/notice";

    @Autowired
    private INoticeService noticeService;

    @RequiresPermissions("system:notice:view")
    @GetMapping
    public String notice() {
        return prefix + "/notice";
    }

    /**
     * Query the announcement list
     */
    @RequiresPermissions("system:notice:list")
    @PostMapping("/list")
    @ResponseBody
    public TableData<Notice> list(Notice notice) {
        startPage();
        List<Notice> list = noticeService.selectNoticeList(notice);
        return getTableData(list);
    }

    /**
     * New announcement
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * Add save announcement
     */
    @RequiresPermissions("system:notice:add")
    @Log(title = "announcement", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public void addSave(@Validated Notice notice) {
        noticeService.save(notice);
    }

    /**
     * Modification announcement
     */
    @GetMapping("/edit/{noticeId}")
    public String edit(@PathVariable("noticeId") Long noticeId, ModelMap mmap) {
        mmap.put("notice", noticeService.getById(noticeId));
        return prefix + "/edit";
    }

    /**
     * Modify save announcement
     */
    @RequiresPermissions("system:notice:edit")
    @Log(title = "announcement", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public void editSave(@Validated Notice notice) {
        noticeService.updateById(notice);
    }

    /**
     * Delete announcement
     */
    @RequiresPermissions("system:notice:remove")
    @Log(title = "announcement", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public void remove(String ids) {
        noticeService.remove(Wrappers.<Notice>lambdaQuery().in(Notice::getNoticeId, StringUtils.split2List(ids)));
    }
}
