package org.crown.framework.web.page;

import org.crown.common.utils.StringUtils;
import org.crown.common.utils.sql.AntiSQLFilter;

import lombok.Getter;
import lombok.Setter;

/**
 * Paging data
 *
 * @author Crown
 */
@Setter
@Getter
public class PageDomain {

    /**
     * Current record start index
     */
    private Integer pageNum;
    /**
     * Display the number of records per page
     */
    private Integer pageSize;
    /**
     * Sort column
     */
    private String sort;
    /**
     * Sorting direction "desc" or "asc".
     */
    private String order;
    /**
     * Sort table alias
     */
    private String tableAlias;
    /**
     * Query Count
     */
    private boolean searchCount;

    public String getOrderBy() {
        if (StringUtils.isEmpty(sort)) {
            return "";
        }
        if (StringUtils.isNotEmpty(tableAlias)) {
            return tableAlias + "." + StringUtils.toUnderScoreCase(sort) + " " + order;
        }

        return StringUtils.toUnderScoreCase(sort) + " " + order;
    }

    public String getSort() {
        return AntiSQLFilter.getSafeValue(sort);
    }

}
