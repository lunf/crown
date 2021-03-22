package org.crown.framework.web.page;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Table paging data object
 *
 * @author Crown
 */
@Setter
@Getter
public class TableData<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * total
     */
    private long total;
    /**
     * List data
     */
    private List<T> rows;

    /**
     * Tabular data object
     */
    public TableData() {
    }

    /**
     * Pagination
     *
     * @param list  List data
     * @param total total
     */
    public TableData(List<T> list, int total) {
        this.rows = list;
        this.total = total;
    }

}
