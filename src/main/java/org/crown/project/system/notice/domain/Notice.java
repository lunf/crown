package org.crown.project.system.notice.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.crown.framework.web.domain.BaseEntity;

import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Getter;
import lombok.Setter;

/**
 * Notice Announcement Form sys_notice
 *
 * @author Crown
 */
@Setter
@Getter
public class Notice extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * Announcement ID
     */
    @TableId
    private Long noticeId;
    /**
     * Announcement title
     */
    @NotBlank(message = "Announcement title cannot be empty")
    @Size(max = 50, message = "Announcement title cannot exceed 50 characters")
    private String noticeTitle;
    /**
     * Announcement type (1 notification, 2 announcement)
     */
    private String noticeType;
    /**
     * Announcement content
     */
    private String noticeContent;
    /**
     * Announcement status (0 normal, 1 closed)
     */
    private String status;

    /**
     * Remarks
     */
    private String remark;

}
