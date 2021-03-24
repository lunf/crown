package org.crown.project.system.dept.service;

import java.util.List;

import org.crown.framework.service.BaseService;
import org.crown.framework.web.domain.Ztree;
import org.crown.project.system.dept.domain.Dept;
import org.crown.project.system.role.domain.Role;

/**
 * Department management service layer
 *
 * @author Crown
 */
public interface IDeptService extends BaseService<Dept> {

    /**
     * Query department management data
     *
     * @param dept Department Information
     * @return Departmental Information Collection
     */
    List<Dept> selectDeptList(Dept dept);

    /**
     * Query department management tree
     *
     * @param dept Department Information
     * @return All department information
     */
    List<Ztree> selectDeptTree(Dept dept);

    /**
     * Query menu based on role ID
     *
     * @param role Role object
     * @return Menu list
     */
    List<Ztree> roleDeptTreeData(Role role);

    /**
     * Query whether there are users in the department
     *
     * @param deptId Department ID
     * @return Result true exists false does not exist
     */
    boolean checkDeptExistUser(Long deptId);

    /**
     * Add and save department information
     *
     * @param dept Department Information
     * @return result
     */
    boolean insertDept(Dept dept);

    /**
     * Modify and save department information
     *
     * @param dept Department Information
     * @return result
     */
    boolean updateDept(Dept dept);

    /**
     * Query information based on department ID
     *
     * @param deptId Department ID
     * @return Department Information
     */
    Dept selectDeptById(Long deptId);

    /**
     * Verify that the department name is unique
     *
     * @param dept Department Information
     * @return result
     */
    boolean checkDeptNameUnique(Dept dept);
}
