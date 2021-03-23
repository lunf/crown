package org.crown.project.system.dept.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.crown.framework.mapper.BaseMapper;
import org.crown.project.system.dept.domain.Dept;

/**
 * Department Management Data Layer
 *
 * @author Crown
 */
@Mapper
public interface DeptMapper extends BaseMapper<Dept> {

    /**
     * Query department management data
     *
     * @param dept Department Information
     * @return Departmental Information Collection
     */
    List<Dept> selectDeptList(Dept dept);

    /**
     * Modify the child element relationship
     *
     * @param depts Child element
     * @return Result
     */
    int updateDeptChildren(@Param("depts") List<Dept> depts);

    /**
     * Query information based on department ID
     *
     * @param deptId Department ID
     * @return Department Information
     */
    Dept selectDeptById(Long deptId);

    /**
     * Query department based on role ID
     *
     * @param roleId Role ID
     * @return Department list
     */
    List<String> selectRoleDeptTree(Long roleId);

    /**
     * Modify the status of the parent department of the department
     *
     * @param dept Department
     */
    void updateDeptStatus(Dept dept);

    /**
     * Query all sub-departments based on ID
     *
     * @param deptId Department ID
     * @return Department list
     */
    List<Dept> selectChildrenDeptById(Long deptId);
}
