package org.crown.project.system.notice.service;

import java.util.List;

import org.crown.framework.service.BaseService;
import org.crown.project.system.notice.domain.Notice;

/**
 * Announcement Service Layer
 *
 * @author Crown
 */
public interface INoticeService extends BaseService<Notice> {

    /**
     * Query the announcement list
     *
     * @param notice official news
     * @return Announcement collection
     */
    List<Notice> selectNoticeList(Notice notice);
}