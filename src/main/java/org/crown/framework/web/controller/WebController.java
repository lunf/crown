package org.crown.framework.web.controller;

import java.util.List;

import org.crown.common.utils.StringUtils;
import org.crown.common.utils.TypeUtils;
import org.crown.common.utils.security.ShiroUtils;
import org.crown.framework.web.page.PageDomain;
import org.crown.framework.web.page.TableData;
import org.crown.project.system.user.domain.User;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * Web layer general data processing
 *
 * @author Crown
 */
public class WebController<Entity> extends SuperController<Entity> {

    /**
     * Encapsulate the paging object
     */
    protected PageDomain getPageDomain() {
        // Number of pages
        Integer pageNum = TypeUtils.castToInt(request.getParameter(PAGE_NUM), 1);
        // Paging Size
        Integer pageSize = TypeUtils.castToInt(request.getParameter(PAGE_SIZE), DEFAULT_PAGE_SIZE);
        // Whether to query paging
        Boolean searchCount = TypeUtils.castToBoolean(request.getParameter(SEARCH_COUNT), true);
        pageSize = pageSize > MAX_PAGE_SIZE ? MAX_PAGE_SIZE : pageSize;
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(pageNum);
        pageDomain.setPageSize(pageSize);
        pageDomain.setSort(request.getParameter(PAGE_SORT));
        pageDomain.setOrder(request.getParameter(PAGE_ORDER));
        pageDomain.setTableAlias(getAlias());
        pageDomain.setSearchCount(searchCount);
        return pageDomain;
    }

    /**
     * Set request paging data
     */
    protected void startPage() {
        PageDomain pageDomain = getPageDomain();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        boolean searchCount = pageDomain.isSearchCount();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
            PageHelper.startPage(pageNum, pageSize, searchCount).setOrderBy(pageDomain.getOrderBy());
        }
    }

    /**
     * Paging data in response to requests
     */
    protected <T> TableData<T> getTableData(List<T> list) {
        TableData rspData = new TableData();
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }

    /**
     * Page jump
     */
    public String redirect(String url) {
        return StringUtils.format("redirect:{}", url);
    }

    public User getSysUser() {
        return ShiroUtils.getSysUser();
    }

    public void setSysUser(User user) {
        ShiroUtils.setSysUser(user);
    }

    public Long getUserId() {
        return getSysUser().getUserId();
    }

    public String getLoginName() {
        return getSysUser().getLoginName();
    }
}
