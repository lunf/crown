package org.crown.project.system.post.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.crown.common.annotation.Log;
import org.crown.common.enums.BusinessType;
import org.crown.common.utils.poi.ExcelUtils;
import org.crown.framework.enums.ErrorCodeEnum;
import org.crown.framework.model.ExcelDTO;
import org.crown.framework.utils.ApiAssert;
import org.crown.framework.web.controller.WebController;
import org.crown.framework.web.page.TableData;
import org.crown.project.system.post.domain.Post;
import org.crown.project.system.post.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Post information operation processing
 *
 * @author Crown
 */
@Controller
@RequestMapping("/system/post")
public class PostController extends WebController<Post> {

    private final String prefix = "system/post";

    @Autowired
    private IPostService postService;

    @RequiresPermissions("system:post:view")
    @GetMapping
    public String operlog() {
        return prefix + "/post";
    }

    @RequiresPermissions("system:post:list")
    @PostMapping("/list")
    @ResponseBody
    public TableData<Post> list(Post post) {
        startPage();
        List<Post> list = postService.selectPostList(post);
        return getTableData(list);
    }

    @Log(title = "岗位管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:post:export")
    @PostMapping("/export")
    @ResponseBody
    public ExcelDTO export(Post post) {
        List<Post> list = postService.selectPostList(post);
        ExcelUtils<Post> util = new ExcelUtils<>(Post.class);
        return new ExcelDTO(util.exportExcel(list, "岗位数据"));

    }

    @RequiresPermissions("system:post:remove")
    @Log(title = "Job management", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public void remove(String ids) {
        postService.deletePostByIds(ids);
    }

    /**
     * New job
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * Add save job
     */
    @RequiresPermissions("system:post:add")
    @Log(title = "Job management", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public void addSave(@Validated Post post) {
        ApiAssert.isTrue(ErrorCodeEnum.POST_NAME_EXIST.overrideMsg("Position Title[" + post.getPostName() + "]exists"), postService.checkPostNameUnique(post));
        ApiAssert.isTrue(ErrorCodeEnum.POST_CODE_EXIST.overrideMsg("Post code[" + post.getPostCode() + "]exists"), postService.checkPostCodeUnique(post));
        postService.save(post);
    }

    /**
     * Modify position
     */
    @GetMapping("/edit/{postId}")
    public String edit(@PathVariable("postId") Long postId, ModelMap mmap) {
        mmap.put("post", postService.getById(postId));
        return prefix + "/edit";
    }

    /**
     * Modify save post
     */
    @RequiresPermissions("system:post:edit")
    @Log(title = "Job management", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public void editSave(@Validated Post post) {
        ApiAssert.isTrue(ErrorCodeEnum.POST_NAME_EXIST.overrideMsg("Position Title[" + post.getPostName() + "]exists"), postService.checkPostNameUnique(post));
        ApiAssert.isTrue(ErrorCodeEnum.POST_CODE_EXIST.overrideMsg("Post code[" + post.getPostCode() + "]exists"), postService.checkPostCodeUnique(post));
        postService.updateById(post);
    }

    /**
     * Check post name
     */
    @PostMapping("/checkPostNameUnique")
    @ResponseBody
    public boolean checkPostNameUnique(Post post) {
        return postService.checkPostNameUnique(post);
    }

    /**
     * Check post code
     */
    @PostMapping("/checkPostCodeUnique")
    @ResponseBody
    public boolean checkPostCodeUnique(Post post) {
        return postService.checkPostCodeUnique(post);
    }
}
