package org.crown.project.system.notice.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.crown.framework.mapper.BaseMapper;
import org.crown.project.system.notice.domain.Notice;

/**
 * Announcement data layer
 *
 * @author Crown
 */
@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {

}