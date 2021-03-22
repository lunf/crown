package org.crown.framework.web.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.crown.common.utils.JsonUtils;
import org.crown.common.utils.Maps;
import org.crown.common.utils.TypeUtils;

import com.baomidou.mybatisplus.annotation.TableField;

import lombok.Getter;
import lombok.Setter;

/**
 * Entity base class
 *
 * @author Crown
 */
@Setter
@Getter
public class BaseQueryParams implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Search value
     */
    @TableField(exist = false)
    private String searchValue;

    /**
     * Request parameter
     */
    @TableField(exist = false)
    private Map<String, Object> params;

    public Map<String, Object> getParams() {
        if (params == null) {
            params = Maps.<String, Object>builder().build();
        }
        return params;
    }

    public Date getBeginTime() {
        return TypeUtils.castToDate(getParams().get("beginTime"));
    }

    public Date getEndTime() {
        return TypeUtils.castToDate(getParams().get("endTime"));
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ": " + JsonUtils.toJson(this);
    }

}
