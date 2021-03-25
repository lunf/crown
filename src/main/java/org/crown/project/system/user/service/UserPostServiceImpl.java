package org.crown.project.system.user.service;

import org.crown.framework.service.impl.BaseServiceImpl;
import org.crown.project.system.user.domain.UserPost;
import org.crown.project.system.user.mapper.UserPostMapper;
import org.springframework.stereotype.Service;

/**
 * User post business layer processing
 *
 * @author Crown
 */
@Service
public class UserPostServiceImpl extends BaseServiceImpl<UserPostMapper, UserPost> implements IUserPostService {

}
